package com.pandaz.usercenter.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.DictInfoDTO;
import com.pandaz.commons.dto.usercenter.DictTypeDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.R;
import com.pandaz.usercenter.custom.constants.UrlConstants;
import com.pandaz.usercenter.entity.DictInfoEntity;
import com.pandaz.usercenter.service.DictInfoService;
import com.pandaz.usercenter.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典信息
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
@RestController
@RequestMapping("/dict/info")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DictInfoController {

    /**
     * 字典信息服务
     */
    private final DictInfoService dictInfoService;

    /**
     * 工具类
     */
    private final ControllerUtil controllerUtil;

    /**
     * 查询方法
     *
     * @param dictInfoDTO 查询条件
     * @return 组信息
     */
    @GetMapping(UrlConstants.GET)
    public R<DictInfoDTO> get(@Valid DictInfoDTO dictInfoDTO) {
        DictInfoDTO result = BeanCopyUtil.copy(dictInfoService.getWithTypeName(dictInfoDTO.getCode()), DictInfoDTO.class);
        return new R<>(result);
    }

    /**
     * 分页方法
     *
     * @param dictInfoDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    public R<Map<String, Object>> getPage(DictInfoDTO dictInfoDTO) {
        IPage<DictInfoEntity> page = dictInfoService.getPage(BeanCopyUtil.copy(dictInfoDTO, DictInfoEntity.class));
        return new R<>(BeanCopyUtil.convertToMap(page, DictInfoDTO.class));
    }

    /**
     * 新增方法
     *
     * @param dictInfoDTO 字典信息
     * @return 字典信息
     */
    @PostMapping(UrlConstants.INSERT)
    public R<String> insert(@RequestBody DictInfoDTO dictInfoDTO, Principal principal) {
        check(dictInfoDTO);
        DictInfoEntity dictInfoEntity = BeanCopyUtil.copy(dictInfoDTO, DictInfoEntity.class);
        dictInfoEntity.setCreatedBy(principal.getName());
        dictInfoEntity.setCreatedDate(LocalDateTime.now());
        dictInfoService.insert(dictInfoEntity);
        return R.success();
    }

    /**
     * 更新方法
     *
     * @param dictInfoDTO 字典信息
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public R<String> update(@Valid @RequestBody DictInfoDTO dictInfoDTO, Principal principal) {
        check(dictInfoDTO);
        DictInfoEntity dictInfoEntity = BeanCopyUtil.copy(dictInfoDTO, DictInfoEntity.class);
        dictInfoEntity.setUpdatedBy(principal.getName());
        dictInfoEntity.setUpdatedDate(LocalDateTime.now());
        dictInfoService.updateByCode(dictInfoEntity);
        return R.success();
    }

    /**
     * 删除方法
     *
     * @param codes 字典信息
     * @return 执行结果
     */
    @DeleteMapping(UrlConstants.DELETE)
    public R<String> delete(@RequestBody List<String> codes, Principal principal) {
        dictInfoService.deleteByCodes(principal.getName(), LocalDateTime.now(), codes);
        return R.success();
    }

    /**
     * 获取全部字典类型
     *
     * @return 字典类型
     */
    @GetMapping("/listAllTypes")
    public R<List<DictTypeDTO>> listAllTypes() {
        return new R<>(controllerUtil.listAllTypes());
    }

    /**
     * 检查方法
     *
     * @param dictInfoDTO 字典信息
     */
    private void check(DictInfoDTO dictInfoDTO) {
        Assert.hasText(dictInfoDTO.getName(), "字典信息名称不能为空");
        Assert.hasText(dictInfoDTO.getTypeCode(), "请关联字典类型");
    }
}

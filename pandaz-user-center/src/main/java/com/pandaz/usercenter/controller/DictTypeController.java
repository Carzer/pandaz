package com.pandaz.usercenter.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.DictTypeDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.R;
import com.pandaz.usercenter.custom.constants.UrlConstants;
import com.pandaz.usercenter.entity.DictTypeEntity;
import com.pandaz.usercenter.service.DictTypeService;
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
 * 字典类型
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
@RestController
@RequestMapping("/dict/type")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class DictTypeController {

    /**
     * 字典类型服务
     */
    private final DictTypeService dictTypeService;

    /**
     * 工具类
     */
    private final ControllerUtil<DictTypeService> controllerUtil;

    /**
     * 查询方法
     *
     * @param dictTypeDTO 字典类型
     * @return 字典类型
     */
    public R<DictTypeDTO> get(DictTypeDTO dictTypeDTO) {
        DictTypeDTO result = BeanCopyUtil.copy(dictTypeService.findByCode(dictTypeDTO.getCode()), DictTypeDTO.class);
        return new R<>(result);
    }

    /**
     * 分页方法
     *
     * @param dictTypeDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    public R<Map<String, Object>> getPage(DictTypeDTO dictTypeDTO) {
        IPage<DictTypeEntity> page = dictTypeService.getPage(BeanCopyUtil.copy(dictTypeDTO, DictTypeEntity.class));
        return new R<>(BeanCopyUtil.convertToMap(page, DictTypeDTO.class));
    }

    /**
     * 插入方法
     *
     * @param dictTypeDTO 字典类型
     * @return 执行结果
     */
    @PostMapping(UrlConstants.INSERT)
    public R<String> insert(@Valid @RequestBody DictTypeDTO dictTypeDTO, Principal principal) {
        check(dictTypeDTO);
        DictTypeEntity dictTypeEntity = BeanCopyUtil.copy(dictTypeDTO, DictTypeEntity.class);
        dictTypeEntity.setCreatedBy(principal.getName());
        dictTypeEntity.setCreatedDate(LocalDateTime.now());
        dictTypeService.insert(dictTypeEntity);
        return R.success();
    }

    /**
     * 更新方法
     *
     * @param dictTypeDTO 字典类型
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public R<String> update(@Valid @RequestBody DictTypeDTO dictTypeDTO, Principal principal) {
        check(dictTypeDTO);
        DictTypeEntity dictTypeEntity = BeanCopyUtil.copy(dictTypeDTO, DictTypeEntity.class);
        dictTypeEntity.setUpdatedBy(principal.getName());
        dictTypeEntity.setUpdatedDate(LocalDateTime.now());
        dictTypeService.updateByCode(dictTypeEntity);
        return R.success();
    }

    /**
     * 删除方法
     *
     * @param codes 组信息
     * @return 执行结果
     */
    @DeleteMapping(UrlConstants.DELETE)
    public R<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(dictTypeService, principal.getName(), LocalDateTime.now(), codes);
    }

    /**
     * 检查相关信息
     *
     * @param dictTypeDTO 字典类型
     */
    private void check(DictTypeDTO dictTypeDTO) {
        Assert.hasText(dictTypeDTO.getName(), "字典类型名称不能为空");
    }
}

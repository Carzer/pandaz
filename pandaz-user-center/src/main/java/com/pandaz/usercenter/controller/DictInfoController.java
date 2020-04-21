package com.pandaz.usercenter.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.DictInfoDTO;
import com.pandaz.commons.dto.usercenter.DictTypeDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
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
    private final ControllerUtil<DictInfoService> controllerUtil;

    /**
     * 查询方法
     *
     * @param dictInfoDTO 查询条件
     * @return 组信息
     */
    @GetMapping(UrlConstants.GET)
    public ExecuteResult<DictInfoDTO> get(@Valid DictInfoDTO dictInfoDTO) {
        ExecuteResult<DictInfoDTO> result = new ExecuteResult<>();
        try {
            result.setData(BeanCopyUtil.copy(dictInfoService.getWithTypeName(dictInfoDTO.getCode()), DictInfoDTO.class));
        } catch (Exception e) {
            log.error("查询方法异常：", e);
            result.setError(controllerUtil.errorMsg(e, "查询方法异常"));
        }
        return result;
    }

    /**
     * 分页方法
     *
     * @param dictInfoDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    public ExecuteResult<Map<String, Object>> getPage(DictInfoDTO dictInfoDTO) {
        ExecuteResult<Map<String, Object>> result = new ExecuteResult<>();
        try {
            IPage<DictInfoEntity> page = dictInfoService.getPage(BeanCopyUtil.copy(dictInfoDTO, DictInfoEntity.class));
            result.setData(BeanCopyUtil.convertToMap(page, DictInfoDTO.class));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(controllerUtil.errorMsg(e, "分页查询异常"));
        }
        return result;
    }

    /**
     * 新增方法
     *
     * @param dictInfoDTO 字典信息
     * @return 字典信息
     */
    @PostMapping(UrlConstants.INSERT)
    public ExecuteResult<DictInfoDTO> insert(@RequestBody DictInfoDTO dictInfoDTO, Principal principal) {
        ExecuteResult<DictInfoDTO> result = new ExecuteResult<>();
        check(dictInfoDTO);
        try {
            DictInfoEntity dictInfoEntity = BeanCopyUtil.copy(dictInfoDTO, DictInfoEntity.class);
            dictInfoEntity.setCreatedBy(principal.getName());
            dictInfoEntity.setCreatedDate(LocalDateTime.now());
            dictInfoService.insert(dictInfoEntity);
            result.setData(BeanCopyUtil.copy(dictInfoEntity, dictInfoDTO));
        } catch (Exception e) {
            log.error("插入方法异常：", e);
            result.setError(controllerUtil.errorMsg(e, "插入方法异常"));
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param dictInfoDTO 字典信息
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public ExecuteResult<String> update(@Valid @RequestBody DictInfoDTO dictInfoDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        check(dictInfoDTO);
        try {
            DictInfoEntity dictInfoEntity = BeanCopyUtil.copy(dictInfoDTO, DictInfoEntity.class);
            dictInfoEntity.setUpdatedBy(principal.getName());
            dictInfoEntity.setUpdatedDate(LocalDateTime.now());
            dictInfoService.updateByCode(dictInfoEntity);
            result.setData("更新成功");
        } catch (Exception e) {
            log.error("更新方法异常：", e);
            result.setError(controllerUtil.errorMsg(e, "更新方法异常"));
        }
        return result;
    }

    /**
     * 删除方法
     *
     * @param codes 字典信息
     * @return 执行结果
     */
    @DeleteMapping(UrlConstants.DELETE)
    public ExecuteResult<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(dictInfoService, principal.getName(), LocalDateTime.now(), codes);
    }

    /**
     * 获取全部字典类型
     *
     * @return 字典类型
     */
    @GetMapping("/listAllTypes")
    public ExecuteResult<List<DictTypeDTO>> listAllTypes() {
        ExecuteResult<List<DictTypeDTO>> result = new ExecuteResult<>();
        try {
            result.setData(controllerUtil.listAllTypes());
        } catch (Exception e) {
            log.error("获取全部字典类型异常：", e);
            result.setError(controllerUtil.errorMsg(e, "获取全部字典类型异常"));
        }
        return result;
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

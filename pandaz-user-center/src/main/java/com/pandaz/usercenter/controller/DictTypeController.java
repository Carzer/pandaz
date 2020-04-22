package com.pandaz.usercenter.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.DictTypeDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.Result;
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
    public Result<DictTypeDTO> get(DictTypeDTO dictTypeDTO) {
        Result<DictTypeDTO> result = new Result<>();
        try {
            result.setData(BeanCopyUtil.copy(dictTypeService.findByCode(dictTypeDTO.getCode()), DictTypeDTO.class));
        } catch (Exception e) {
            log.error("新增字典类型异常：", e);
            result.setError(controllerUtil.errorMsg(e, "异常"));
        }
        return result;
    }

    /**
     * 分页方法
     *
     * @param dictTypeDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    public Result<Map<String, Object>> getPage(DictTypeDTO dictTypeDTO) {
        Result<Map<String, Object>> result = new Result<>();
        try {
            IPage<DictTypeEntity> page = dictTypeService.getPage(BeanCopyUtil.copy(dictTypeDTO, DictTypeEntity.class));
            result.setData(BeanCopyUtil.convertToMap(page, DictTypeDTO.class));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(controllerUtil.errorMsg(e, "分页查询异常"));
        }
        return result;
    }

    /**
     * 插入方法
     *
     * @param dictTypeDTO 字典类型
     * @return 执行结果
     */
    @PostMapping(UrlConstants.INSERT)
    public Result<DictTypeDTO> insert(@Valid @RequestBody DictTypeDTO dictTypeDTO, Principal principal) {
        Result<DictTypeDTO> result = new Result<>();
        check(dictTypeDTO);
        try {
            DictTypeEntity dictTypeEntity = BeanCopyUtil.copy(dictTypeDTO, DictTypeEntity.class);
            dictTypeEntity.setCreatedBy(principal.getName());
            dictTypeEntity.setCreatedDate(LocalDateTime.now());
            dictTypeService.insert(dictTypeEntity);
            result.setData(BeanCopyUtil.copy(dictTypeEntity, DictTypeDTO.class));
        } catch (Exception e) {
            log.error("新增字典类型异常：", e);
            result.setError(controllerUtil.errorMsg(e, "新增字典类型异常"));
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param dictTypeDTO 字典类型
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public Result<String> update(@Valid @RequestBody DictTypeDTO dictTypeDTO, Principal principal) {
        Result<String> result = new Result<>();
        check(dictTypeDTO);
        try {
            DictTypeEntity dictTypeEntity = BeanCopyUtil.copy(dictTypeDTO, DictTypeEntity.class);
            dictTypeEntity.setUpdatedBy(principal.getName());
            dictTypeEntity.setUpdatedDate(LocalDateTime.now());
            dictTypeService.updateByCode(dictTypeEntity);
            result.setData("更新成功");
        } catch (Exception e) {
            log.error("更新字典类型异常：", e);
            result.setError(controllerUtil.errorMsg(e, "更新字典类型异常"));
        }
        return result;
    }

    /**
     * 删除方法
     *
     * @param codes 组信息
     * @return 执行结果
     */
    @DeleteMapping(UrlConstants.DELETE)
    public Result<String> delete(@RequestBody List<String> codes, Principal principal) {
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

package com.pandaz.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pandaz.commons.dto.usercenter.DictTypeDTO;
import com.pandaz.commons.util.BeanCopierUtil;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.usercenter.entity.DictTypeEntity;
import com.pandaz.usercenter.service.DictTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
@RestController
@RequestMapping("/dictType")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class DictTypeController {

    /**
     * 字典类型服务
     */
    private final DictTypeService dictTypeService;

    /**
     * 插入方法
     *
     * @param dictTypeDTO 字典类型
     * @return 执行结果
     */
    @PostMapping
    public ExecuteResult<DictTypeEntity> insert(@Valid @RequestBody DictTypeDTO dictTypeDTO) {
        ExecuteResult<DictTypeEntity> result = new ExecuteResult<>();
        try {
            DictTypeEntity dictTypeEntity = BeanCopierUtil.copy(dictTypeDTO, DictTypeEntity.class);
            result.setSuccess(dictTypeService.save(dictTypeEntity));
        } catch (Exception e) {
            log.error("新增字典类型异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param dictTypeDTO 字典类型
     * @return 执行结果
     */
    @PutMapping
    public ExecuteResult<DictTypeEntity> update(@Valid @RequestBody DictTypeDTO dictTypeDTO) {
        ExecuteResult<DictTypeEntity> result = new ExecuteResult<>();
        try {
            DictTypeEntity dictTypeEntity = BeanCopierUtil.copy(dictTypeDTO, DictTypeEntity.class);
            String code = dictTypeEntity.getCode();
            UpdateWrapper<DictTypeEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("code", code);
            result.setSuccess(dictTypeService.update(dictTypeEntity, updateWrapper));
        } catch (Exception e) {
            log.error("更新字典类型异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }
}

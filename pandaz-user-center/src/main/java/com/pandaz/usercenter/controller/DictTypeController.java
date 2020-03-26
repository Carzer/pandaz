package com.pandaz.usercenter.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.DictTypeDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public ExecuteResult<DictTypeDTO> get(DictTypeDTO dictTypeDTO) {
        ExecuteResult<DictTypeDTO> result = new ExecuteResult<>();
        try {
            result.setData(BeanCopyUtil.copy(dictTypeService.findByCode(dictTypeDTO.getCode()), DictTypeDTO.class));
        } catch (Exception e) {
            log.error("新增字典类型异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 分页方法
     *
     * @param dictTypeDTO 查询信息
     * @return 分页信息
     */
    @GetMapping("/getPage")
    public ExecuteResult<HashMap<String, Object>> getPage(DictTypeDTO dictTypeDTO) {
        ExecuteResult<HashMap<String, Object>> result = new ExecuteResult<>();
        try {
            IPage<DictTypeEntity> page = dictTypeService.getPage(BeanCopyUtil.copy(dictTypeDTO, DictTypeEntity.class));
            result.setData(BeanCopyUtil.convertToMap(page, DictTypeDTO.class));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 获取全部字典类型
     *
     * @return 字典类型
     */
    @GetMapping("/listAll")
    public ExecuteResult<ArrayList<DictTypeDTO>> listAll() {
        ExecuteResult<ArrayList<DictTypeDTO>> result = new ExecuteResult<>();
        try {
            List<DictTypeEntity> list = dictTypeService.list();
            result.setData((ArrayList<DictTypeDTO>) BeanCopyUtil.copyList(list, DictTypeDTO.class));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 插入方法
     *
     * @param dictTypeDTO 字典类型
     * @return 执行结果
     */
    @PostMapping
    public ExecuteResult<DictTypeDTO> insert(@Valid @RequestBody DictTypeDTO dictTypeDTO, Principal principal) {
        ExecuteResult<DictTypeDTO> result = new ExecuteResult<>();
        try {
            check(dictTypeDTO);
            DictTypeEntity dictTypeEntity = BeanCopyUtil.copy(dictTypeDTO, DictTypeEntity.class);
            dictTypeEntity.setCreatedBy(principal.getName());
            dictTypeEntity.setCreatedDate(LocalDateTime.now());
            dictTypeService.insert(dictTypeEntity);
            result.setData(BeanCopyUtil.copy(dictTypeEntity, DictTypeDTO.class));
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
    public ExecuteResult<String> update(@Valid @RequestBody DictTypeDTO dictTypeDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            check(dictTypeDTO);
            DictTypeEntity dictTypeEntity = BeanCopyUtil.copy(dictTypeDTO, DictTypeEntity.class);
            dictTypeEntity.setUpdatedBy(principal.getName());
            dictTypeEntity.setUpdatedDate(LocalDateTime.now());
            dictTypeService.updateByCode(dictTypeEntity);
            result.setData("更新成功");
        } catch (Exception e) {
            log.error("更新字典类型异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 删除方法
     *
     * @param codes 组信息
     * @return 执行结果
     */
    @DeleteMapping
    public ExecuteResult<String> delete(@RequestBody List<String> codes, Principal principal) {
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

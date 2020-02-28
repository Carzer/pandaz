package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.MenuDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.MenuEntity;
import com.pandaz.usercenter.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * 菜单信息
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/menu")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MenuController {

    /**
     * 菜单服务
     */
    private final MenuService menuService;

    /**
     * 查询方法
     *
     * @param menuDTO 查询条件
     * @return 菜单信息
     */
    @GetMapping
    public ExecuteResult<MenuDTO> get(@Valid MenuDTO menuDTO) {
        ExecuteResult<MenuDTO> result = new ExecuteResult<>();
        try {
            result.setData(BeanCopyUtil.copy(menuService.findByCode(menuDTO.getCode()), MenuDTO.class));
        } catch (Exception e) {
            log.error("查询方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 分页方法
     *
     * @param menuDTO 查询信息
     * @return 分页信息
     */
    @GetMapping("/getPage")
    public ExecuteResult<HashMap<String, Object>> getPage(MenuDTO menuDTO) {
        ExecuteResult<HashMap<String, Object>> result = new ExecuteResult<>();
        try {
            IPage<MenuEntity> page = menuService.getPage(BeanCopyUtil.copy(menuDTO, MenuEntity.class));
            result.setData(BeanCopyUtil.convertToMap(page, MenuDTO.class));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 新增方法
     *
     * @param menuDTO 菜单信息
     * @return 菜单信息
     */
    @PostMapping
    public ExecuteResult<MenuDTO> insert(@RequestBody MenuDTO menuDTO, Principal principal) {
        ExecuteResult<MenuDTO> result = new ExecuteResult<>();
        try {
            check(menuDTO);
            MenuEntity menuEntity = BeanCopyUtil.copy(menuDTO, MenuEntity.class);
            menuEntity.setId(UuidUtil.getId());
            menuEntity.setCreatedBy(principal.getName());
            menuEntity.setCreatedDate(LocalDateTime.now());
            result.setData(BeanCopyUtil.copy(menuService.insert(menuEntity), menuDTO));
        } catch (Exception e) {
            log.error("插入方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param menuDTO 菜单信息
     * @return 执行结果
     */
    @PutMapping
    public ExecuteResult<String> update(@Valid @RequestBody MenuDTO menuDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            check(menuDTO);
            MenuEntity menuEntity = BeanCopyUtil.copy(menuDTO, MenuEntity.class);
            menuEntity.setUpdatedBy(principal.getName());
            menuEntity.setUpdatedDate(LocalDateTime.now());
            menuService.updateByCode(menuEntity);
            result.setData("更新成功");
        } catch (Exception e) {
            log.error("更新方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 删除方法
     *
     * @param menuDTO 菜单信息
     * @return 执行结果
     */
    @DeleteMapping
    public ExecuteResult<String> delete(@Valid @RequestBody MenuDTO menuDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            menuDTO.setDeletedBy(principal.getName());
            menuDTO.setDeletedDate(LocalDateTime.now());
            menuService.deleteByCode(BeanCopyUtil.copy(menuDTO, MenuEntity.class));
            result.setData("删除成功");
        } catch (Exception e) {
            log.error("删除方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 检查方法
     *
     * @param menuDTO 菜单信息
     */
    private void check(MenuDTO menuDTO) {
        Assert.hasText(menuDTO.getName(), "菜单名称不能为空");
    }
}

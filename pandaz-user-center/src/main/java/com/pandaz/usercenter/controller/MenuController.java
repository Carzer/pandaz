package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.constants.CommonConstants;
import com.pandaz.commons.dto.usercenter.MenuDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.MenuEntity;
import com.pandaz.usercenter.service.MenuService;
import com.pandaz.usercenter.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
     * 工具类
     */
    private final ControllerUtil<MenuService> controllerUtil;

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
     * 获取所有菜单
     *
     * @param menuDTO 查询信息
     * @return 所有菜单
     */
    @GetMapping("/getAll")
    public ExecuteResult<MenuDTO> getAll(MenuDTO menuDTO) {
        ExecuteResult<MenuDTO> result = new ExecuteResult<>();
        try {
            menuDTO.setCode(CommonConstants.ROOT_MENU_CODE);
            menuDTO.setParentCode(CommonConstants.ROOT_MENU_CODE);
            MenuEntity menuEntity = BeanCopyUtil.copy(menuDTO, MenuEntity.class);
            List<MenuEntity> list = menuService.getAll(menuEntity);
            menuEntity.setChildren(list);
            result.setData(transferToDTO(menuEntity));
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
            menuService.insert(menuEntity);
            result.setData(BeanCopyUtil.copy(menuEntity, menuDTO));
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
     * @param codes 菜单信息
     * @return 执行结果
     */
    @DeleteMapping
    public ExecuteResult<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(menuService, principal.getName(), LocalDateTime.now(), codes);
    }

    /**
     * 检查方法
     *
     * @param menuDTO 菜单信息
     */
    private void check(MenuDTO menuDTO) {
        Assert.hasText(menuDTO.getName(), "菜单名称不能为空");
    }

    /**
     * 将entity及children转换为dto
     *
     * @param menuEntity entity
     * @return dto
     */
    private MenuDTO transferToDTO(MenuEntity menuEntity) {
        MenuDTO menuDTO = new MenuDTO();
        List<MenuEntity> entityList = menuEntity.getChildren();
        if (!CollectionUtils.isEmpty(entityList)) {
            List<MenuDTO> dtoList = new ArrayList<>();
            entityList.forEach(menu -> dtoList.add(transferToDTO(menu)));
            menuDTO.setChildren(dtoList);
        }
        menuDTO.setId(menuEntity.getId());
        menuDTO.setCode(menuEntity.getCode());
        menuDTO.setParentCode(menuEntity.getParentCode());
        menuDTO.setName(menuEntity.getName());
        return menuDTO;
    }

}

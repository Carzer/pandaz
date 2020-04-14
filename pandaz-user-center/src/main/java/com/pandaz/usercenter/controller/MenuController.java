package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.constants.CommonConstants;
import com.pandaz.commons.dto.usercenter.MenuDTO;
import com.pandaz.commons.dto.usercenter.OsInfoDTO;
import com.pandaz.commons.dto.usercenter.PermissionDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.MenuEntity;
import com.pandaz.usercenter.entity.PermissionEntity;
import com.pandaz.usercenter.service.MenuService;
import com.pandaz.usercenter.service.OsInfoService;
import com.pandaz.usercenter.service.PermissionService;
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
import java.util.Map;

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
     * 权限服务
     */
    private final PermissionService permissionService;

    /**
     * 系统信息服务
     */
    private final OsInfoService osInfoService;

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
    @GetMapping("/get")
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
     * 权限分页方法
     *
     * @param permissionDTO 查询信息
     * @return 分页信息
     */
    @GetMapping("/getPermissionPage")
    public ExecuteResult<HashMap<String, Object>> getPermissionPage(PermissionDTO permissionDTO) {
        ExecuteResult<HashMap<String, Object>> result = new ExecuteResult<>();
        try {
            IPage<PermissionEntity> page = permissionService.getPage(BeanCopyUtil.copy(permissionDTO, PermissionEntity.class));
            result.setData(BeanCopyUtil.convertToMap(page, PermissionDTO.class));
        } catch (Exception e) {
            log.error("权限分页查询异常：", e);
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
            MenuEntity menuEntity = BeanCopyUtil.copy(menuDTO, MenuEntity.class);
            menuEntity.setParentCode(CommonConstants.ROOT_MENU_CODE);
            List<MenuEntity> list = menuService.getAll(menuEntity);
            menuEntity.setChildren(list);
            result.setData(transferToDTO(menuEntity));
        } catch (Exception e) {
            log.error("获取所有菜单异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 获取全部系统信息
     *
     * @return 系统信息
     */
    @GetMapping("/osListAll")
    public ExecuteResult<ArrayList<OsInfoDTO>> osListAll() {
        ExecuteResult<ArrayList<OsInfoDTO>> result = new ExecuteResult<>();
        try {
            result.setData((ArrayList<OsInfoDTO>) BeanCopyUtil.copyList(osInfoService.list(), OsInfoDTO.class));
        } catch (Exception e) {
            log.error("获取全部系统信息异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 获取所有菜单
     *
     * @return 所有菜单
     */
    @GetMapping("/listByOsCode")
    public ExecuteResult<ArrayList<MenuDTO>> listByOsCode(String osCode) {
        ExecuteResult<ArrayList<MenuDTO>> result = new ExecuteResult<>();
        try {
            Map<String, Object> map = new HashMap<>(1);
            map.put("os_code", osCode);
            List<MenuEntity> list = menuService.listByMap(map);
            result.setData((ArrayList<MenuDTO>) BeanCopyUtil.copyList(list, MenuDTO.class));
        } catch (Exception e) {
            log.error("获取所有菜单异常：", e);
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
    @PostMapping("/insert")
    public ExecuteResult<MenuDTO> insert(@RequestBody MenuDTO menuDTO, Principal principal) {
        ExecuteResult<MenuDTO> result = new ExecuteResult<>();
        try {
            check(menuDTO);
            MenuEntity menuEntity = BeanCopyUtil.copy(menuDTO, MenuEntity.class);
            menuEntity.setId(UuidUtil.getId());
            menuEntity.setCreatedBy(principal.getName());
            menuEntity.setCreatedDate(LocalDateTime.now());
            menuEntity.setIsLeafNode(Byte.valueOf("1"));
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
    @PutMapping("/update")
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
    @DeleteMapping("/delete")
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
        menuDTO.setOsCode(menuEntity.getOsCode());
        menuDTO.setParentCode(menuEntity.getParentCode());
        menuDTO.setName(menuEntity.getName());
        menuDTO.setUrl(menuEntity.getUrl());
        menuDTO.setRouter(menuEntity.getRouter());
        menuDTO.setRemark(menuEntity.getRemark());
        menuDTO.setLocked(menuEntity.getLocked());
        menuDTO.setSorting(menuEntity.getSorting());
        menuDTO.setIsLeafNode(menuEntity.getIsLeafNode());
        return menuDTO;
    }

}

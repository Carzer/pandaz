package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.MenuDTO;
import com.pandaz.commons.dto.usercenter.OsInfoDTO;
import com.pandaz.commons.dto.usercenter.PermissionDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.R;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.UrlConstants;
import com.pandaz.usercenter.entity.MenuEntity;
import com.pandaz.usercenter.service.MenuService;
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
    private final ControllerUtil controllerUtil;

    /**
     * 查询方法
     *
     * @param menuDTO 查询条件
     * @return 菜单信息
     */
    @GetMapping(UrlConstants.GET)
    public R<MenuDTO> get(@Valid MenuDTO menuDTO) {
        MenuDTO result = BeanCopyUtil.copy(menuService.findByCode(menuDTO.getCode()), MenuDTO.class);
        return new R<>(result);
    }

    /**
     * 分页方法
     *
     * @param menuDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    public R<Map<String, Object>> getPage(MenuDTO menuDTO) {
        IPage<MenuEntity> page = menuService.getPage(BeanCopyUtil.copy(menuDTO, MenuEntity.class));
        return new R<>(BeanCopyUtil.convertToMap(page, MenuDTO.class));
    }

    /**
     * 新增方法
     *
     * @param menuDTO 菜单信息
     * @return 菜单信息
     */
    @PostMapping(UrlConstants.INSERT)
    public R<String> insert(@RequestBody MenuDTO menuDTO, Principal principal) {
        check(menuDTO);
        MenuEntity menuEntity = BeanCopyUtil.copy(menuDTO, MenuEntity.class);
        menuEntity.setId(UuidUtil.getId());
        menuEntity.setCreatedBy(principal.getName());
        menuEntity.setCreatedDate(LocalDateTime.now());
        menuEntity.setIsLeafNode(Byte.valueOf("1"));
        menuService.insert(menuEntity);
        return R.success();
    }

    /**
     * 更新方法
     *
     * @param menuDTO 菜单信息
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public R<String> update(@Valid @RequestBody MenuDTO menuDTO, Principal principal) {
        check(menuDTO);
        MenuEntity menuEntity = BeanCopyUtil.copy(menuDTO, MenuEntity.class);
        menuEntity.setUpdatedBy(principal.getName());
        menuEntity.setUpdatedDate(LocalDateTime.now());
        menuService.updateByCode(menuEntity);
        return R.success();
    }

    /**
     * 删除方法
     *
     * @param codes 菜单信息
     * @return 执行结果
     */
    @DeleteMapping(UrlConstants.DELETE)
    public R<String> delete(@RequestBody List<String> codes, Principal principal) {
        menuService.deleteByCodes(principal.getName(), LocalDateTime.now(), codes);
        return R.success();
    }

    /**
     * 权限分页方法
     *
     * @param permissionDTO 查询信息
     * @return 分页信息
     */
    @GetMapping("/getPermissionPage")
    public R<Map<String, Object>> getPermissionPage(PermissionDTO permissionDTO) {
        return new R<>(controllerUtil.getPermissionPage(permissionDTO));
    }

    /**
     * 获取所有菜单
     *
     * @return 所有菜单
     */
    @GetMapping("/getAll")
    public R<MenuDTO> getAll(MenuDTO menuDTO) {
        return new R<>(controllerUtil.getAllMenu(menuDTO, false));
    }

    /**
     * 获取全部系统信息
     *
     * @return 系统信息
     */
    @GetMapping("/listAllOs")
    public R<List<OsInfoDTO>> listAllOs() {
        return new R<>(controllerUtil.listAllOs());
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

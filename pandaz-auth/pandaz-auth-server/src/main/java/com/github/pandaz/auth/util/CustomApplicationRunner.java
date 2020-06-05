package com.github.pandaz.auth.util;

import com.github.pandaz.auth.custom.CustomProperties;
import com.github.pandaz.auth.service.RolePermissionService;
import com.github.pandaz.auth.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 系统启动时，先初始化权限信息
 *
 * @author Carzer
 * @since 2019-09-03 14:37
 */
@Component
@Slf4j
@Order(1000)
public class CustomApplicationRunner implements ApplicationRunner {

    /**
     * 角色-权限服务
     */
    private RolePermissionService rolePermissionService;

    /**
     * 角色服务
     */
    private RoleService roleService;

    /**
     * 通用配置
     */
    private CustomProperties customProperties;

    /**
     * 执行方法
     *
     * @param args 参数
     */
    @Override
    public void run(ApplicationArguments args) {
        log.debug("开始初始化权限信息");
        String osCode = customProperties.getOsCode();
        List<String> roleList = roleService.listAllRoleCode();
        if (!CollectionUtils.isEmpty(roleList)) {
            roleList.parallelStream().forEach(roleCode ->
                    rolePermissionService.getByOsCodeAndRoleCode(osCode, roleCode)
            );
        }
        log.debug("初始化权限信息结束");
    }

    @Autowired
    public void setRolePermissionService(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setCustomProperties(CustomProperties customProperties) {
        this.customProperties = customProperties;
    }
}
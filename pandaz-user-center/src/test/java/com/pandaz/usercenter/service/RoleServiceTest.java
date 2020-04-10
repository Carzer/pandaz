package com.pandaz.usercenter.service;

import com.pandaz.commons.dto.usercenter.PermissionDTO;
import com.pandaz.commons.dto.usercenter.SimplePermissionDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.PermissionEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色服务测试
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Transactional
@Slf4j
public class RoleServiceTest extends BasisUnitTest {

    private RoleService roleService;

    private RolePermissionService rolePermissionService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setRolePermissionService(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @Test
    public void insert() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode("role_test");
        roleEntity.setName("测试角色");
        roleService.insert(roleEntity);
        log.info("插入{}", roleEntity.getId());
    }

    @Test
    public void findByUserCode() {
    }

    @Test
    public void findBySecurityUser() {
    }

    @Test
    public void getPage() {
        RoleEntity roleEntity = new RoleEntity();
        log.info("分页结果:{}", roleService.getPage(roleEntity));
    }

    @Test
    public void updateByCode() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode("role_test");
        roleEntity.setName("测试角色已变更");
        roleService.updateByCode(roleEntity);
        log.info("更新成功");
    }

    @Test
    public void findByCode() {
        RoleEntity roleEntity = roleService.findByCode("role_test");
        log.info("找到角色：{}", roleEntity);
    }

    @Test
    public void deleteByCode() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode("role_test");
        roleEntity.setDeletedBy("admin");
        roleEntity.setDeletedDate(LocalDateTime.now());
        int size = roleService.deleteByCode(roleEntity);
        log.info("成功删除角色：{}个", size);
    }

    @Test
    public void bindPermission() {
        String operator = "system";
        String roleCode = "ROLE_ADMIN";
        String osCode = "portal";
        String menuCode = "test";
        List<SimplePermissionDTO> simplePermissionDTOList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SimplePermissionDTO permissionDTO = new SimplePermissionDTO();
            permissionDTO.setCode("test_per" + i);
            permissionDTO.setOsCode(osCode);
            permissionDTO.setMenuCode(menuCode);
            simplePermissionDTOList.add(permissionDTO);
        }
        List<PermissionEntity> list = BeanCopyUtil.copyList(simplePermissionDTOList, PermissionEntity.class);
        rolePermissionService.bindPermission(operator, LocalDateTime.now(), roleCode, list);
        
    }
}
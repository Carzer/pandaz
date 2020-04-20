package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.RoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.*;

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

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Test
    public void insert() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode("role_test");
        roleEntity.setName("测试角色");
        int result = roleService.insert(roleEntity);
        assertEquals(1, result);
    }

    @Test
    public void getPage() {
        IPage<RoleEntity> page = roleService.getPage(new RoleEntity());
        assertNotNull(page);
    }

    @Test
    public void updateByCode() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode("role_test");
        roleEntity.setName("测试角色已变更");
        int result = roleService.updateByCode(roleEntity);
        assertThat(result, anything());
    }

    @Test
    public void findByCode() {
        RoleEntity roleEntity = roleService.findByCode("role_test");
        assertThat(roleEntity, anything());
    }

    @Test
    public void deleteByCode() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode("role_test");
        roleEntity.setDeletedBy("admin");
        roleEntity.setDeletedDate(LocalDateTime.now());
        int size = roleService.deleteByCode(roleEntity);
        assertThat(size, anything());
    }

}
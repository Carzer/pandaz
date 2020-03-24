package com.pandaz.usercenter.service;

import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.RoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
}
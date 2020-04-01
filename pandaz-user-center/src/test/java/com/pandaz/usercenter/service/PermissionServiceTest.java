package com.pandaz.usercenter.service;

import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.PermissionEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 权限测试
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Transactional
public class PermissionServiceTest extends BasisUnitTest {

    /**
     * 权限服务
     */
    private PermissionService permissionService;

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Test
    public void insert() {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setCode("per_test");
        permissionEntity.setName("测试权限");
        permissionEntity.setOsCode("portal");
        permissionEntity.setUrl("/permission");
        permissionEntity.setRequestType(Byte.valueOf("1"));
        permissionEntity.setBitDigit(Byte.valueOf("1"));
        permissionEntity.setBitResult(1 >> permissionEntity.getBitDigit());
        permissionService.insert(permissionEntity);
    }

    @Test
    public void findByCode() {
        permissionService.findByCode("per_test");
    }

    @Test
    public void getPage() {
        permissionService.getPage(new PermissionEntity());
    }

    @Test
    public void updateByCode() {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setCode("per_test");
        permissionEntity.setName("测试权限");
        permissionService.updateByCode(permissionEntity);
    }

    @Test
    public void deleteByCode() {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setCode("per_test");
        permissionEntity.setDeletedBy("admin");
        permissionEntity.setDeletedDate(LocalDateTime.now());
        permissionService.deleteByCode(permissionEntity);
    }
}
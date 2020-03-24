package com.pandaz.usercenter.service;

import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.OrganizationEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 组织信息测试
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Transactional
public class OrganizationServiceTest extends BasisUnitTest {

    private OrganizationService organizationService;

    @Autowired
    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Test
    public void findByCode() {
        organizationService.findByCode("org_test");
    }

    @Test
    public void getPage() {
        organizationService.getPage(new OrganizationEntity());
    }

    @Test
    public void insert() {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setCode("org_test");
        organizationEntity.setName("测试组织");
        organizationService.insert(organizationEntity);
    }

    @Test
    public void updateByCode() {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setCode("org_test");
        organizationEntity.setName("测试组织");
        organizationService.updateByCode(organizationEntity);
    }

    @Test
    public void deleteByCode() {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setCode("org_test");
        organizationEntity.setDeletedBy("admin");
        organizationEntity.setDeletedDate(LocalDateTime.now());
        organizationService.deleteByCode(organizationEntity);
    }
}
package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.OrganizationEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.*;

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
        OrganizationEntity test = organizationService.findByCode("org_test");
        assertThat(test, anything());
    }

    @Test
    public void getPage() {
        IPage<OrganizationEntity> page = organizationService.getPage(new OrganizationEntity());
        assertNotNull(page);
    }

    @Test
    public void insert() {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setCode("org_test");
        organizationEntity.setName("测试组织");
        int result = organizationService.insert(organizationEntity);
        assertEquals(1, result);
    }

    @Test
    public void updateByCode() {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setCode("org_test");
        organizationEntity.setName("测试组织");
        int result = organizationService.updateByCode(organizationEntity);
        assertThat(result, anything());
    }

    @Test
    public void deleteByCode() {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setCode("org_test");
        organizationEntity.setDeletedBy("admin");
        organizationEntity.setDeletedDate(LocalDateTime.now());
        int result = organizationService.deleteByCode(organizationEntity);
        assertThat(result, anything());
    }
}
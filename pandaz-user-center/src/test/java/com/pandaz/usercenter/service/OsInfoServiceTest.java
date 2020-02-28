package com.pandaz.usercenter.service;

import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.OsInfoEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统信息测试
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Transactional
public class OsInfoServiceTest extends BasisUnitTest {

    private OsInfoService osInfoService;

    @Autowired
    public void setOsInfoService(OsInfoService osInfoService) {
        this.osInfoService = osInfoService;
    }

    @Test
    public void insert() {
        OsInfoEntity osInfoEntity = new OsInfoEntity();
        osInfoEntity.setCode("os_test");
        osInfoEntity.setName("测试系统");
        osInfoService.insert(osInfoEntity);
    }

    @Test
    public void findByCode() {
        osInfoService.findByCode("os_test");
    }

    @Test
    public void getPage() {
        osInfoService.getPage(new OsInfoEntity());
    }

    @Test
    public void updateByCode() {
        OsInfoEntity osInfoEntity = new OsInfoEntity();
        osInfoEntity.setCode("os_test");
        osInfoEntity.setName("测试系统");
        osInfoService.updateByCode(osInfoEntity);
    }

    @Test
    public void deleteByCode() {
        OsInfoEntity osInfoEntity = new OsInfoEntity();
        osInfoEntity.setCode("os_test");
        osInfoService.deleteByCode(osInfoEntity);
    }
}
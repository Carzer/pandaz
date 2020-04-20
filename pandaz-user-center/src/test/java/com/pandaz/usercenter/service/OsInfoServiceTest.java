package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.OsInfoEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.*;

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
        int result = osInfoService.insert(osInfoEntity);
        assertEquals(1, result);
    }

    @Test
    public void findByCode() {
        OsInfoEntity test = osInfoService.findByCode("os_test");
        assertThat(test, anything());
    }

    @Test
    public void getPage() {
        IPage<OsInfoEntity> page = osInfoService.getPage(new OsInfoEntity());
        assertNotNull(page);
    }

    @Test
    public void updateByCode() {
        OsInfoEntity osInfoEntity = new OsInfoEntity();
        osInfoEntity.setCode("os_test");
        osInfoEntity.setName("测试系统");
        int result = osInfoService.updateByCode(osInfoEntity);
        assertThat(result, anything());
    }

    @Test
    public void deleteByCode() {
        OsInfoEntity osInfoEntity = new OsInfoEntity();
        osInfoEntity.setCode("os_test");
        osInfoEntity.setDeletedBy("admin");
        osInfoEntity.setDeletedDate(LocalDateTime.now());
        int result = osInfoService.deleteByCode(osInfoEntity);
        assertThat(result, anything());
    }
}
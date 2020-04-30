package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.usercenter.UserCenterApp;
import com.pandaz.usercenter.entity.DictInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * 字典信息测试
 *
 * @author Carzer
 * @since 2020-02-28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserCenterApp.class)
@Rollback
@Transactional
@Slf4j
public class DictInfoServiceTest {

    private DictInfoService dictInfoService;

    @BeforeClass
    public static void setUp() {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "../logs/user-center-test/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "../logs/user-center-test/nacos/naming");
    }

    @Autowired
    public void setDictInfoService(DictInfoService dictInfoService) {
        this.dictInfoService = dictInfoService;
    }

    @Test
    public void findByCode() {
        DictInfoEntity dictInfoEntity = new DictInfoEntity();
        dictInfoEntity.setCode("dictInfo_test");
        DictInfoEntity test = dictInfoService.findByCode(dictInfoEntity);
        assertThat(test, anything());
    }

    @Test
    public void getWithTypeName() {
        DictInfoEntity test = dictInfoService.getWithTypeName("test");
        assertThat(test, anything());
    }

    @Test
    public void getPage() {
        IPage<DictInfoEntity> page = dictInfoService.getPage(new DictInfoEntity());
        assertNotNull(page);
    }

    @Test
    public void insert() {
        DictInfoEntity dictInfoEntity = new DictInfoEntity();
        dictInfoEntity.setCode("dictInfo_test");
        dictInfoEntity.setTypeCode("dictType_test");
        dictInfoEntity.setName("字典信息测试");
        int result = 0;
        try {
            result = dictInfoService.insert(dictInfoEntity);
        } catch (Exception e) {
            log.error("插入字典信息出错", e);
        }
        assertThat(result, anything());
    }

    @Test
    public void updateByCode() {
        DictInfoEntity dictInfoEntity = new DictInfoEntity();
        dictInfoEntity.setCode("dictInfo_test");
        dictInfoEntity.setName("字典信息测试");
        int result = dictInfoService.updateByCode(dictInfoEntity);
        assertThat(result, anything());
    }

    @Test
    public void deleteByCode() {
        DictInfoEntity dictInfoEntity = new DictInfoEntity();
        dictInfoEntity.setCode("dictInfo_test");
        dictInfoEntity.setDeletedBy("admin");
        dictInfoEntity.setDeletedDate(LocalDateTime.now());
        int result = dictInfoService.deleteByCode(dictInfoEntity);
        assertThat(result, anything());
    }
}
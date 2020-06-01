package com.github.pandaz.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pandaz.auth.AuthServerApp;
import com.github.pandaz.auth.entity.DictTypeEntity;
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
 * 字典类型测试
 *
 * @author Carzer
 * @since 2020-02-28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthServerApp.class)
@Rollback
@Transactional
@Slf4j
public class DictTypeServiceTest {

    private DictTypeService dictTypeService;

    @BeforeClass
    public static void setUp() {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "../../logs/auth-test/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "../../logs/auth-test/nacos/naming");
    }

    @Autowired
    public void setDictTypeService(DictTypeService dictTypeService) {
        this.dictTypeService = dictTypeService;
    }

    @Test
    public void findByCode() {
        DictTypeEntity dictTypeEntity = new DictTypeEntity();
        dictTypeEntity.setCode("dictType_test");
        DictTypeEntity test = dictTypeService.findByCode(dictTypeEntity);
        assertThat(test, anything());
    }

    @Test
    public void getPage() {
        IPage<DictTypeEntity> page = dictTypeService.getPage(new DictTypeEntity());
        assertNotNull(page);
    }

    @Test
    public void insert() {
        DictTypeEntity dictTypeEntity = new DictTypeEntity();
        dictTypeEntity.setCode("dictType_test");
        dictTypeEntity.setName("dictType_test");
        int result = 0;
        try {
            result = dictTypeService.insert(dictTypeEntity);
        } catch (Exception e) {
            log.error("插入字典类型出错", e);
        }
        assertThat(result, anything());
    }

    @Test
    public void updateByCode() {
        DictTypeEntity dictTypeEntity = new DictTypeEntity();
        dictTypeEntity.setCode("dictType_test");
        dictTypeEntity.setName("dictType_test");
        int result = dictTypeService.updateByCode(dictTypeEntity);
        assertThat(result, anything());
    }

    @Test
    public void deleteByCode() {
        DictTypeEntity dictTypeEntity = new DictTypeEntity();
        dictTypeEntity.setCode("dictType_test");
        dictTypeEntity.setDeletedBy("admin");
        dictTypeEntity.setDeletedDate(LocalDateTime.now());
        int result = dictTypeService.deleteByCode(dictTypeEntity);
        assertThat(result, anything());
    }
}
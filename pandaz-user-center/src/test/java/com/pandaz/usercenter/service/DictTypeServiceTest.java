package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.DictTypeEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.*;

/**
 * 字典类型测试
 *
 * @author Carzer
 * @since 2020-02-28
 */
@Transactional
public class DictTypeServiceTest extends BasisUnitTest {

    private DictTypeService dictTypeService;

    @Autowired
    public void setDictTypeService(DictTypeService dictTypeService) {
        this.dictTypeService = dictTypeService;
    }

    @Test
    public void findByCode() {
        DictTypeEntity test = dictTypeService.findByCode("dictType_test");
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
        int result = dictTypeService.insert(dictTypeEntity);
        assertEquals(1, result);
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
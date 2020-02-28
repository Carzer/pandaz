package com.pandaz.usercenter.service;

import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.DictTypeEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
        dictTypeService.findByCode("dictType_test");
    }

    @Test
    public void getPage() {
        dictTypeService.getPage(new DictTypeEntity());
    }

    @Test
    public void insert() {
        DictTypeEntity dictTypeEntity = new DictTypeEntity();
        dictTypeEntity.setCode("dictType_test");
        dictTypeEntity.setName("dictType_test");
        dictTypeService.insert(dictTypeEntity);
    }

    @Test
    public void updateByCode() {
        DictTypeEntity dictTypeEntity = new DictTypeEntity();
        dictTypeEntity.setCode("dictType_test");
        dictTypeEntity.setName("dictType_test");
        dictTypeService.updateByCode(dictTypeEntity);
    }

    @Test
    public void deleteByCode() {
        DictTypeEntity dictTypeEntity = new DictTypeEntity();
        dictTypeEntity.setCode("dictType_test");
        dictTypeService.deleteByCode(dictTypeEntity);
    }
}
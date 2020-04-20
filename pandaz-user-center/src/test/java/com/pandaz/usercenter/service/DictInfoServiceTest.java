package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.DictInfoEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.*;

/**
 * 字典信息测试
 *
 * @author Carzer
 * @since 2020-02-28
 */
@Transactional
public class DictInfoServiceTest extends BasisUnitTest {

    private DictInfoService dictInfoService;

    @Autowired
    public void setDictInfoService(DictInfoService dictInfoService) {
        this.dictInfoService = dictInfoService;
    }

    @Test
    public void findByCode() {
        DictInfoEntity test = dictInfoService.findByCode("dictInfo_test");
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
        int result = dictInfoService.insert(dictInfoEntity);
        assertEquals(1, result);
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
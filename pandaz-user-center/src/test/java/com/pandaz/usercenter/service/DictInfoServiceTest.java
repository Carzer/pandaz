package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.DictInfoEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        dictInfoService.findByCode("dictInfo_test");
    }

    @Test
    public void getWithTypeName() {
        DictInfoEntity test = dictInfoService.getWithTypeName("test");
        System.out.println(test);
    }

    @Test
    public void getPage() {
        IPage<DictInfoEntity> page = dictInfoService.getPage(new DictInfoEntity());
        System.out.println(page);
//        dictInfoService.getPage(new DictInfoEntity());
    }

    @Test
    public void insert() {
        DictInfoEntity dictInfoEntity = new DictInfoEntity();
        dictInfoEntity.setCode("dictInfo_test");
        dictInfoEntity.setTypeCode("dictType_test");
        dictInfoEntity.setName("字典信息测试");
        dictInfoService.insert(dictInfoEntity);
    }

    @Test
    public void updateByCode() {
        DictInfoEntity dictInfoEntity = new DictInfoEntity();
        dictInfoEntity.setCode("dictInfo_test");
        dictInfoEntity.setName("字典信息测试");
        dictInfoService.updateByCode(dictInfoEntity);
    }

    @Test
    public void deleteByCode() {
        DictInfoEntity dictInfoEntity = new DictInfoEntity();
        dictInfoEntity.setCode("dictInfo_test");
        dictInfoEntity.setDeletedBy("admin");
        dictInfoEntity.setDeletedDate(LocalDateTime.now());
        dictInfoService.deleteByCode(dictInfoEntity);
    }
}
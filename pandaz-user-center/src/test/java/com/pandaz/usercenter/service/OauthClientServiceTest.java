package com.pandaz.usercenter.service;

import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.OauthClientEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * 客户端信息测试
 *
 * @author Carzer
 * @since 2020-02-28
 */
@Transactional
public class OauthClientServiceTest extends BasisUnitTest {

    private OauthClientService oauthClientService;

    @Autowired
    public void setOauthClientService(OauthClientService oauthClientService) {
        this.oauthClientService = oauthClientService;
    }

    @Test
    public void deleteByClientId() {
        OauthClientEntity oauthClientEntity = new OauthClientEntity();
        oauthClientEntity.setClientId("test");
        oauthClientEntity.setDeletedBy("admin");
        oauthClientEntity.setDeletedDate(LocalDateTime.now());
        oauthClientService.deleteByClientId(oauthClientEntity);
    }

    @Test
    public void findByClientId() {
        oauthClientService.loadClientByClientId("test");
        oauthClientService.findByClientId("test");
    }

    @Test
    public void updateByClientId() {
        OauthClientEntity oauthClientEntity = new OauthClientEntity();
        oauthClientEntity.setClientId("client_test");
        oauthClientService.updateByClientId(oauthClientEntity);
    }

    @Test
    public void insert() {
        OauthClientEntity oauthClientEntity = new OauthClientEntity();
        oauthClientEntity.setClientId("client_test");
        oauthClientService.insert(oauthClientEntity);
    }

    @Test
    public void getPage() {
        OauthClientEntity oauthClientEntity = new OauthClientEntity();
        oauthClientEntity.setClientId("client_test");
        oauthClientService.getPage(oauthClientEntity);
    }
}
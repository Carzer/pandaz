package com.pandaz.usercenter.controller;


import com.pandaz.commons.controller.BaseController;
import com.pandaz.commons.dto.usercenter.OauthClientDTO;
import com.pandaz.commons.service.BaseService;
import com.pandaz.usercenter.entity.OauthClientEntity;
import com.pandaz.usercenter.service.OauthClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * oauth2客户端信息
 *
 * @author Carzer
 * @since 2020-01-02
 */
@RestController
@RequestMapping("/oauthClient")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OauthClientController extends BaseController<OauthClientDTO, OauthClientEntity> {

    /**
     * 客户端服务
     */
    private final OauthClientService oauthClientService;

    /**
     * 获取服务方法
     *
     * @return 服务
     */
    @Override
    protected BaseService<OauthClientEntity> getBaseService() {
        return this.oauthClientService;
    }

    /**
     * 检查方法
     *
     * @param oauthClientDTO 客户端信息
     */
    @Override
    protected void check(OauthClientDTO oauthClientDTO) {
        Assert.hasText(oauthClientDTO.getClientId(), "clientId不能为空");
    }
}

package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.OauthClientEntity;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * <p>
 * oauth2客户端信息 服务类
 * </p>
 *
 * @author Carzer
 * @since 2020-01-02
 */
public interface OauthClientService extends IService<OauthClientEntity> {

    /**
     * 根据客户端ID查询客户端
     *
     * @param id 客户端ID
     * @return 客户端
     */
    ClientDetails loadClientByClientId(String id);
}

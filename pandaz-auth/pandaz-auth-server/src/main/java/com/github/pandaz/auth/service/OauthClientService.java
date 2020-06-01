package com.github.pandaz.auth.service;

import com.github.pandaz.auth.entity.OauthClientEntity;
import com.github.pandaz.commons.service.BaseService;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * <p>
 * oauth2客户端信息 服务类
 * </p>
 *
 * @author Carzer
 * @since 2020-01-02
 */
public interface OauthClientService extends BaseService<OauthClientEntity> {

    /**
     * 根据客户端ID查询客户端
     *
     * @param clientId 客户端ID
     * @return 客户端
     */
    ClientDetails loadClientByClientId(String clientId);

    /**
     * 根据客户端ID删除
     *
     * @param oauthClientEntity 客户端信息
     * @return 执行结果
     */
    int deleteByClientId(OauthClientEntity oauthClientEntity);

    /**
     * 根据客户端ID查询
     *
     * @param oauthClientEntity 客户端ID
     * @return 执行结果
     */
    OauthClientEntity findByClientId(OauthClientEntity oauthClientEntity);

    /**
     * 根据客户端ID更新
     *
     * @param oauthClientEntity 客户端信息
     * @return 执行结果
     */
    int updateByClientId(OauthClientEntity oauthClientEntity);
}

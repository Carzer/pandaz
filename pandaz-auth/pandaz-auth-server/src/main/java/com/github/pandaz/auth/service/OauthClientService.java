package com.github.pandaz.auth.service;

import com.github.pandaz.auth.entity.ClientEntity;
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
public interface OauthClientService extends BaseService<ClientEntity> {

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
     * @param clientEntity 客户端信息
     * @return 执行结果
     */
    int deleteByClientId(ClientEntity clientEntity);

    /**
     * 根据客户端ID查询
     *
     * @param clientEntity 客户端ID
     * @return 执行结果
     */
    ClientEntity findByClientId(ClientEntity clientEntity);

    /**
     * 根据客户端ID更新
     *
     * @param clientEntity 客户端信息
     * @return 执行结果
     */
    int updateByClientId(ClientEntity clientEntity);
}

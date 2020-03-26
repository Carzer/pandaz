package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
public interface OauthClientService extends UcBaseService<OauthClientEntity> {

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
     * @param clientId 客户端ID
     * @return 执行结果
     */
    OauthClientEntity findByClientId(String clientId);

    /**
     * 根据客户端ID更新
     *
     * @param oauthClientEntity 客户端信息
     * @return 执行结果
     */
    int updateByClientId(OauthClientEntity oauthClientEntity);

    /**
     * 分页方法
     *
     * @param oauthClientEntity 查询条件
     * @return 分页
     */
    IPage<OauthClientEntity> getPage(OauthClientEntity oauthClientEntity);

}

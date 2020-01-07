package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.usercenter.entity.OauthClientEntity;
import com.pandaz.usercenter.mapper.OauthClientMapper;
import com.pandaz.usercenter.service.OauthClientService;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * oauth2客户端信息 服务实现类
 * </p>
 *
 * @author Carzer
 * @since 2020-01-02
 */
@Service
@Slf4j
@CacheConfig(cacheManager = "secondaryCacheManager", cacheNames = {"user-center:oauth_client"})
@SuppressWarnings("unchecked")
public class OauthClientServiceImpl extends ServiceImpl<OauthClientMapper, OauthClientEntity> implements OauthClientService {

    /**
     * 根据客户端ID查询客户端
     * <p>
     * {@link TokenEndpoint#postAccessToken}
     *
     * @param id 客户端ID
     * @return 客户端
     */
    @Cacheable(key = "#id")
    @Override
    public ClientDetails loadClientByClientId(String id) {
        QueryWrapper<OauthClientEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id", id);
        OauthClientEntity oauthClientEntity = this.baseMapper.selectOne(queryWrapper);
        if (oauthClientEntity == null) {
            throw new NoSuchClientException(String.format("No client with requested id: %s", id));
        }
        return convertClient(oauthClientEntity);
    }

    /**
     * 转换client类型
     *
     * @param oauthClientEntity 客户端
     * @return 客户端
     */
    private BaseClientDetails convertClient(OauthClientEntity oauthClientEntity) {
        BaseClientDetails baseClientDetails = new BaseClientDetails(
                oauthClientEntity.getClientId(), oauthClientEntity.getResourceIds(),
                oauthClientEntity.getScope(), oauthClientEntity.getAuthorizedGrantTypes(),
                oauthClientEntity.getAuthorities(), oauthClientEntity.getWebServerRedirectUri()
        );
        baseClientDetails.setClientSecret(oauthClientEntity.getClientSecret());
        if (oauthClientEntity.getAccessTokenValidity() != null) {
            baseClientDetails.setAccessTokenValiditySeconds(oauthClientEntity.getAccessTokenValidity());
        }
        if (oauthClientEntity.getRefreshTokenValidity() != null) {
            baseClientDetails.setRefreshTokenValiditySeconds(oauthClientEntity.getRefreshTokenValidity());
        }
        String json = oauthClientEntity.getAdditionalInformation();
        if (StringUtils.hasText(json)) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> additionalInformation = mapper.readValue(json, Map.class);
                baseClientDetails.setAdditionalInformation(additionalInformation);
            } catch (IOException e) {
                log.warn("读取附加信息出错了");
            }
        }
        if (oauthClientEntity.getAutoApprove() != null) {
            baseClientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(oauthClientEntity.getAutoApprove()));
        }
        return baseClientDetails;
    }
}

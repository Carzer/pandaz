package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.CustomPasswordEncoder;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.OauthClientEntity;
import com.pandaz.usercenter.mapper.OauthClientMapper;
import com.pandaz.usercenter.service.OauthClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OauthClientServiceImpl extends ServiceImpl<OauthClientMapper, OauthClientEntity> implements OauthClientService {

    /**
     * oauth2客户端信息 mapper
     */
    private final OauthClientMapper oauthClientMapper;

    /**
     * clientIdColumn
     */
    private static final String CLIENT_ID_COLUMN = "client_id";

    /**
     * 根据客户端ID查询客户端
     * <p>
     * {@link TokenEndpoint#postAccessToken}
     *
     * @param clientId 客户端ID
     * @return 客户端
     */
    @Cacheable(key = "#clientId")
    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        if (!StringUtils.hasText(clientId)) {
            return null;
        }
        QueryWrapper<OauthClientEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CLIENT_ID_COLUMN, clientId);
        OauthClientEntity oauthClientEntity = oauthClientMapper.selectOne(queryWrapper);
        if (oauthClientEntity == null) {
            throw new NoSuchClientException(String.format("No client with requested id: %s", clientId));
        }
        return convertClient(oauthClientEntity);
    }

    /**
     * 根据客户端ID删除
     *
     * @param oauthClientEntity 客户端信息
     * @return 执行结果
     */
    @CacheEvict(key = "#oauthClientEntity.clientId")
    @Override
    public int deleteByClientId(OauthClientEntity oauthClientEntity) {
        return oauthClientMapper.logicDelete(oauthClientEntity);
    }

    /**
     * 根据客户端ID查询
     *
     * @param clientId 客户端ID
     * @return 执行结果
     */
    @Override
    public OauthClientEntity findByClientId(String clientId) {
        QueryWrapper<OauthClientEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CLIENT_ID_COLUMN, clientId);
        return oauthClientMapper.selectOne(queryWrapper);
    }

    /**
     * 根据客户端ID更新
     *
     * @param oauthClientEntity 客户端信息
     * @return 执行结果
     */
    @CacheEvict(key = "#oauthClientEntity.clientId")
    @Override
    public int updateByClientId(OauthClientEntity oauthClientEntity) {
        UpdateWrapper<OauthClientEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(CLIENT_ID_COLUMN, oauthClientEntity.getClientId());
        return oauthClientMapper.update(oauthClientEntity, updateWrapper);
    }

    /**
     * 插入方法
     *
     * @param oauthClientEntity 客户端信息
     * @return 结果
     */
    @Override
    public int insert(OauthClientEntity oauthClientEntity) {
        String clientId = oauthClientEntity.getClientId();
        //判断是否重复
        if (StringUtils.hasText(clientId)) {
            if (findByClientId(clientId) != null) {
                throw new IllegalArgumentException("客户端编码已存在");
            }
        } else {
            oauthClientEntity.setClientId(UuidUtil.getId());
        }
        String rawPass = oauthClientEntity.getClientSecret();
        String encodedPass = SysConstants.DEFAULT_ENCODED_PASS;
        if (StringUtils.hasText(rawPass)) {
            CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();
            encodedPass = passwordEncoder.encode(rawPass);
        }
        oauthClientEntity.setClientSecret(encodedPass);
        oauthClientEntity.setId(UuidUtil.getId());
        return oauthClientMapper.insertSelective(oauthClientEntity);
    }

    /**
     * 分页方法
     *
     * @param oauthClientEntity 查询条件
     * @return 分页
     */
    @Override
    public IPage<OauthClientEntity> getPage(OauthClientEntity oauthClientEntity) {
        Page<OauthClientEntity> page = new Page<>(oauthClientEntity.getPageNum(), oauthClientEntity.getPageSize());
        QueryWrapper<OauthClientEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(oauthClientEntity.getClientId())) {
            queryWrapper.likeRight(CLIENT_ID_COLUMN, oauthClientEntity.getClientId());
        }
        if (StringUtils.hasText(oauthClientEntity.getClientName())) {
            queryWrapper.likeRight("client_name", oauthClientEntity.getClientName());
        }
        if (oauthClientEntity.getLocked() != null) {
            queryWrapper.eq("locked", oauthClientEntity.getLocked());
        }
        if (oauthClientEntity.getStartDate() != null) {
            queryWrapper.ge(SysConstants.CREATED_DATE_COLUMN, oauthClientEntity.getStartDate());
        }
        if (oauthClientEntity.getEndDate() != null) {
            queryWrapper.le(SysConstants.CREATED_DATE_COLUMN, oauthClientEntity.getEndDate());
        }
        queryWrapper.orderByDesc(SysConstants.CREATED_DATE_COLUMN);
        return page(page, queryWrapper);
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

    /**
     * 批量删除
     *
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       编码
     * @return 执行结果
     */
    @Override
    public int deleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return 0;
        }
        Map<String, Object> map = new HashMap<>(3);
        map.put("deletedBy", deletedBy);
        map.put("deletedDate", deletedDate);
        map.put("list", codes);
        return oauthClientMapper.batchLogicDelete(map);
    }

}

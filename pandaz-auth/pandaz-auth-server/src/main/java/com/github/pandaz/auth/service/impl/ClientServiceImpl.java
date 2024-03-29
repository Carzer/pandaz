package com.github.pandaz.auth.service.impl;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.auth.entity.ClientEntity;
import com.github.pandaz.auth.mapper.ClientMapper;
import com.github.pandaz.auth.service.ClientService;
import com.github.pandaz.commons.util.CustomPasswordEncoder;
import com.github.pandaz.commons.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@SuppressWarnings("unchecked")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientServiceImpl extends ServiceImpl<ClientMapper, ClientEntity> implements ClientService {

    /**
     * oauth2客户端信息 mapper
     */
    private final ClientMapper clientMapper;

    /**
     * 根据客户端ID查询客户端
     * <p>
     * {@link TokenEndpoint#postAccessToken}
     *
     * @param clientId 客户端ID
     * @return 客户端
     */
    @Cached(name = "client:", key = "#clientId")
    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        if (!StringUtils.hasText(clientId)) {
            return null;
        }
        QueryWrapper<ClientEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ClientEntity::getClientId, clientId);
        ClientEntity clientEntity = clientMapper.selectOne(queryWrapper);
        if (clientEntity == null) {
            throw new NoSuchClientException(String.format("No client with requested id: %s", clientId));
        }
        return convertClient(clientEntity);
    }

    /**
     * 根据客户端ID删除
     *
     * @param clientEntity 客户端信息
     * @return 执行结果
     */
    @CacheInvalidate(name = "client:", key = "#oauthClientEntity.clientId")
    @Override
    public int deleteByClientId(ClientEntity clientEntity) {
        return clientMapper.logicDeleteByCode(clientEntity);
    }

    /**
     * 根据编码删除
     *
     * @param clientEntity entity
     * @return 执行结果
     */
    @Override
    public int logicDeleteByCode(ClientEntity clientEntity) {
        return deleteByClientId(clientEntity);
    }

    /**
     * 根据客户端ID查询
     *
     * @param clientEntity 客户端ID
     * @return 执行结果
     */
    @Override
    public ClientEntity findByClientId(ClientEntity clientEntity) {
        QueryWrapper<ClientEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ClientEntity::getClientId, clientEntity.getClientId());
        return clientMapper.selectOne(queryWrapper);
    }

    /**
     * 根据编码查询
     *
     * @param entity entity
     * @return 查询结果
     */
    @Override
    public ClientEntity findByCode(ClientEntity entity) {
        return findByClientId(entity);
    }

    /**
     * 根据客户端ID更新
     *
     * @param clientEntity 客户端信息
     * @return 执行结果
     */
    @CacheInvalidate(name = "client:", key = "#oauthClientEntity.clientId")
    @Override
    public int updateByClientId(ClientEntity clientEntity) {
        UpdateWrapper<ClientEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(ClientEntity::getClientId, clientEntity.getClientId());
        return clientMapper.update(clientEntity, updateWrapper);
    }

    /**
     * 根据编码更新
     *
     * @param clientEntity 客户端信息
     * @return 执行结果
     */
    @Override
    public int updateByCode(ClientEntity clientEntity) {
        return updateByClientId(clientEntity);
    }

    /**
     * 插入方法
     *
     * @param clientEntity 客户端信息
     * @return 结果
     */
    @Override
    public int insert(ClientEntity clientEntity) {
        //判断是否重复
        if (StringUtils.hasText(clientEntity.getClientId())) {
            if (findByClientId(clientEntity) != null) {
                throw new IllegalArgumentException("客户端编码重复");
            }
        } else {
            clientEntity.setClientId(UuidUtil.getId());
        }
        String rawPass = clientEntity.getClientSecret();
        String encodedPass = SysConstants.DEFAULT_ENCODED_PASS;
        if (StringUtils.hasText(rawPass)) {
            CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();
            encodedPass = passwordEncoder.encode(rawPass);
        }
        clientEntity.setClientSecret(encodedPass);
        clientEntity.setId(UuidUtil.getId());
        return clientMapper.insertSelective(clientEntity);
    }

    /**
     * 分页方法
     *
     * @param clientEntity 查询条件
     * @return 分页
     */
    @Override
    public IPage<ClientEntity> getPage(ClientEntity clientEntity) {
        Page<ClientEntity> page = new Page<>(clientEntity.getPageNum(), clientEntity.getPageSize());
        QueryWrapper<ClientEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().likeRight(StringUtils.hasText(clientEntity.getClientId()), ClientEntity::getClientId, clientEntity.getClientId());
        queryWrapper.lambda().likeRight(StringUtils.hasText(clientEntity.getClientName()), ClientEntity::getClientName, clientEntity.getClientName());
        queryWrapper.lambda().eq(clientEntity.getLocked() != null, ClientEntity::getLocked, clientEntity.getLocked());
        queryWrapper.lambda().ge(clientEntity.getStartDate() != null, ClientEntity::getCreatedDate, clientEntity.getStartDate());
        queryWrapper.lambda().le(clientEntity.getEndDate() != null, ClientEntity::getCreatedDate, clientEntity.getEndDate());
        queryWrapper.lambda().orderByDesc(ClientEntity::getCreatedDate);
        return page(page, queryWrapper);
    }

    /**
     * 转换client类型
     *
     * @param clientEntity 客户端
     * @return 客户端
     */
    private BaseClientDetails convertClient(ClientEntity clientEntity) {
        BaseClientDetails baseClientDetails = new BaseClientDetails(
                clientEntity.getClientId(), clientEntity.getResourceIds(),
                clientEntity.getScope(), clientEntity.getAuthorizedGrantTypes(),
                clientEntity.getAuthorities(), clientEntity.getWebServerRedirectUri()
        );
        baseClientDetails.setClientSecret(clientEntity.getClientSecret());
        if (clientEntity.getAccessTokenValidity() != null) {
            baseClientDetails.setAccessTokenValiditySeconds(clientEntity.getAccessTokenValidity());
        }
        if (clientEntity.getRefreshTokenValidity() != null) {
            baseClientDetails.setRefreshTokenValiditySeconds(clientEntity.getRefreshTokenValidity());
        }
        String json = clientEntity.getAdditionalInformation();
        if (StringUtils.hasText(json)) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> additionalInformation = mapper.readValue(json, Map.class);
                baseClientDetails.setAdditionalInformation(additionalInformation);
            } catch (IOException e) {
                log.warn("读取附加信息出错了");
            }
        }
        if (clientEntity.getAutoApprove() != null) {
            baseClientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(clientEntity.getAutoApprove()));
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
    public int logicDeleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return 0;
        }
        Map<String, Object> map = new HashMap<>(3);
        map.put("deletedBy", deletedBy);
        map.put("deletedDate", deletedDate);
        map.put("list", codes);
        return clientMapper.logicDeleteByCodes(map);
    }
}

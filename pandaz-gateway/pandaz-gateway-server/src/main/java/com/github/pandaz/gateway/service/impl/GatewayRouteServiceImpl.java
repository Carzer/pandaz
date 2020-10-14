package com.github.pandaz.gateway.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.gateway.entity.GatewayRouteEntity;
import com.github.pandaz.gateway.mapper.GatewayRouteMapper;
import com.github.pandaz.gateway.service.GatewayRouteService;
import com.github.pandaz.commons.constants.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 网关路由表 服务实现类
 *
 * @author Carzer
 * @since 2020-06-15
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GatewayRouteServiceImpl extends ServiceImpl<GatewayRouteMapper, GatewayRouteEntity> implements GatewayRouteService {

    /**
     * 路由mapper
     */
    private final GatewayRouteMapper gatewayRouteMapper;

    /**
     * 列出所有路由信息
     *
     * @return 路由信息
     */
    @Override
    @Cached(name = CommonConstants.ROUTE_KEY, cacheType = CacheType.BOTH, localExpire = 10, expire = 60, timeUnit = TimeUnit.MINUTES)
    public List<GatewayRouteEntity> listAll() {
        return gatewayRouteMapper.selectList(Wrappers.emptyWrapper());
    }

    /**
     * 插入方法
     *
     * @param gatewayRouteEntity 插入方法
     * @return 执行结果
     */
    @Override
    public int insert(GatewayRouteEntity gatewayRouteEntity) {
        return gatewayRouteMapper.insertSelective(gatewayRouteEntity);
    }
}

package com.github.pandaz.gateway.mapper;

import com.github.pandaz.gateway.entity.GatewayRouteEntity;
import com.github.pandaz.commons.mapper.BasisMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 网关路由表 Mapper 接口
 *
 * @author Carzer
 * @since 2020-06-15
 */
@Mapper
public interface GatewayRouteMapper extends BasisMapper<GatewayRouteEntity> {

}

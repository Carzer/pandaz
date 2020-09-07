package com.github.pandaz.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 网关路由表
 *
 * @author Carzer
 * @since 2020-06-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("api_gateway_route")
public class GatewayRouteEntity extends BaseEntity {

    private static final long serialVersionUID = -4448301455268929428L;

    /**
     * 路由ID
     */
    @TableField("route_id")
    private String routeId;

    /**
     * URI
     */
    @TableField("uri")
    private String uri;

    /**
     * 判定器
     */
    @TableField("predicates")
    private String predicates;

    /**
     * 过滤器
     */
    @TableField("filters")
    private String filters;

    /**
     * 排序
     */
    @TableField("route_order")
    private Integer routeOrder;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
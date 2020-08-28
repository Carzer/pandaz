package com.github.pandaz.commons.dto.auth;

import com.github.pandaz.commons.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 网关路由表
 *
 * @author Carzer
 * @since 2020-06-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GatewayRouteDTO", description = "路由信息")
public class GatewayRouteDTO extends BaseDTO {

    private static final long serialVersionUID = -4448301455268929428L;

    /**
     * 路由ID
     */
    @ApiModelProperty("路由ID")
    @Size(min = 1, max = 255)
    private String routeId;

    /**
     * URI
     */
    @ApiModelProperty("URI")
    @NotEmpty
    @Size(min = 1, max = 255)
    private String uri;

    /**
     * 判定器
     */
    @ApiModelProperty("判定器")
    private String predicates;

    /**
     * 过滤器
     */
    @ApiModelProperty("过滤器")
    private String filters;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer routeOrder;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @Size(max = 500)
    private String description;
}
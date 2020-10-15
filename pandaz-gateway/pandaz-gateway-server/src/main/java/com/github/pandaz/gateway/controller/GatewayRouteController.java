package com.github.pandaz.gateway.controller;


import com.github.pandaz.commons.controller.BaseController;
import com.github.pandaz.commons.service.BaseService;
import com.github.pandaz.commons.util.BeanCopyUtil;
import com.github.pandaz.commons.util.R;
import com.github.pandaz.gateway.dto.GatewayRouteDTO;
import com.github.pandaz.gateway.entity.GatewayRouteEntity;
import com.github.pandaz.gateway.service.GatewayRouteService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 网关路由表
 *
 * @author Carzer
 * @since 2020-06-15
 */
@RestController
@RequestMapping("/gatewayRoute")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GatewayRouteController extends BaseController<GatewayRouteDTO, GatewayRouteEntity> {

    /**
     * 路由表服务
     */
    private final GatewayRouteService gatewayRouteService;

    /**
     * 获取服务方法
     *
     * @return 服务
     */
    @Override
    protected BaseService<GatewayRouteEntity> getBaseService() {
        return this.gatewayRouteService;
    }

    /**
     * 获取所有路由表
     *
     * @return 路由表
     */
    @ApiOperation(value = "获取所有路由表", notes = "获取所有路由表")
    @GetMapping("/getAll")
    public R<List<GatewayRouteDTO>> getAll() {
        List<GatewayRouteDTO> list = BeanCopyUtil.copyList(gatewayRouteService.listAll(), GatewayRouteDTO.class);
        return new R<>(list);
    }

    /**
     * 检查方法
     *
     * @param gatewayRouteDTO 字典信息
     */
    @Override
    protected void check(GatewayRouteDTO gatewayRouteDTO) {
        Assert.hasText(gatewayRouteDTO.getUri(), "uri不能为空");
    }
}

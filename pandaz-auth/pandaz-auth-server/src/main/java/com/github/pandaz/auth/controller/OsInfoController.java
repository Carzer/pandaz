package com.github.pandaz.auth.controller;

import com.github.pandaz.auth.dto.OsInfoDTO;
import com.github.pandaz.auth.entity.OsInfoEntity;
import com.github.pandaz.auth.service.OsInfoService;
import com.github.pandaz.commons.annotations.security.PreAuth;
import com.github.pandaz.commons.controller.BaseController;
import com.github.pandaz.commons.service.BaseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统信息
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/osInfo")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "OsInfo", tags = "系统信息")
@PreAuth("osInfo")
public class OsInfoController extends BaseController<OsInfoDTO, OsInfoEntity> {

    /**
     * 系统信息服务
     */
    private final OsInfoService osInfoService;

    /**
     * 获取服务方法
     *
     * @return 获取服务
     */
    @Override
    protected BaseService<OsInfoEntity> getBaseService() {
        return this.osInfoService;
    }

    /**
     * 检查方法
     *
     * @param osInfoDTO 系统信息
     */
    @Override
    protected void check(OsInfoDTO osInfoDTO) {
        Assert.hasText(osInfoDTO.getName(), "系统名称不能为空");
    }
}

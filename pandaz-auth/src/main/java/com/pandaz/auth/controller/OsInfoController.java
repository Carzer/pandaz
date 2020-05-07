package com.pandaz.auth.controller;

import com.pandaz.commons.controller.BaseController;
import com.pandaz.commons.dto.auth.OsInfoDTO;
import com.pandaz.commons.service.BaseService;
import com.pandaz.auth.entity.OsInfoEntity;
import com.pandaz.auth.service.OsInfoService;
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

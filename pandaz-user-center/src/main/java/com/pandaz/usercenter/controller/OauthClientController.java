package com.pandaz.usercenter.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.OauthClientDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.R;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.UrlConstants;
import com.pandaz.usercenter.entity.OauthClientEntity;
import com.pandaz.usercenter.service.OauthClientService;
import com.pandaz.usercenter.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * oauth2客户端信息
 *
 * @author Carzer
 * @since 2020-01-02
 */
@RestController
@RequestMapping("/oauthClient")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OauthClientController {

    /**
     * 客户端服务
     */
    private final OauthClientService oauthClientService;

    /**
     * 工具类
     */
    private final ControllerUtil<OauthClientService> controllerUtil;

    /**
     * 查询方法
     *
     * @param oauthClientDTO 查询条件
     * @return 客户端信息
     */
    @GetMapping(UrlConstants.GET)
    public R<OauthClientDTO> get(@Valid OauthClientDTO oauthClientDTO) {
        OauthClientDTO result = BeanCopyUtil.copy(oauthClientService.findByClientId(oauthClientDTO.getClientId()), OauthClientDTO.class);
        return new R<>(result);
    }

    /**
     * 分页方法
     *
     * @param oauthClientDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    public R<Map<String, Object>> getPage(OauthClientDTO oauthClientDTO) {
        IPage<OauthClientEntity> page = oauthClientService.getPage(BeanCopyUtil.copy(oauthClientDTO, OauthClientEntity.class));
        return new R<>(BeanCopyUtil.convertToMap(page, OauthClientDTO.class));
    }

    /**
     * 新增方法
     *
     * @param oauthClientDTO 客户端信息
     * @return 客户端信息
     */
    @PostMapping(UrlConstants.INSERT)
    public R<OauthClientDTO> insert(@RequestBody OauthClientDTO oauthClientDTO, Principal principal) {
        check(oauthClientDTO);
        OauthClientEntity oauthClientEntity = BeanCopyUtil.copy(oauthClientDTO, OauthClientEntity.class);
        oauthClientEntity.setId(UuidUtil.getId());
        oauthClientEntity.setCreatedBy(principal.getName());
        oauthClientEntity.setCreatedDate(LocalDateTime.now());
        oauthClientService.insert(oauthClientEntity);
        return R.success();
    }

    /**
     * 更新方法
     *
     * @param oauthClientDTO 客户端信息
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public R<String> update(@Valid @RequestBody OauthClientDTO oauthClientDTO, Principal principal) {
        check(oauthClientDTO);
        OauthClientEntity oauthClientEntity = BeanCopyUtil.copy(oauthClientDTO, OauthClientEntity.class);
        oauthClientEntity.setUpdatedBy(principal.getName());
        oauthClientEntity.setUpdatedDate(LocalDateTime.now());
        oauthClientService.updateByClientId(oauthClientEntity);
        return R.success();
    }

    /**
     * 删除方法
     *
     * @param codes 客户端信息
     * @return 执行结果
     */
    @DeleteMapping(UrlConstants.DELETE)
    public R<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(oauthClientService, principal.getName(), LocalDateTime.now(), codes);
    }

    /**
     * 检查方法
     *
     * @param oauthClientDTO 客户端信息
     */
    private void check(OauthClientDTO oauthClientDTO) {
        Assert.hasText(oauthClientDTO.getClientId(), "clientId不能为空");
    }
}

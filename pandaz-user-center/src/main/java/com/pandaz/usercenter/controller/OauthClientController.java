package com.pandaz.usercenter.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.OauthClientDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
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
import java.util.HashMap;
import java.util.List;

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
    public ExecuteResult<OauthClientDTO> get(@Valid OauthClientDTO oauthClientDTO) {
        ExecuteResult<OauthClientDTO> result = new ExecuteResult<>();
        try {
            result.setData(BeanCopyUtil.copy(oauthClientService.findByClientId(oauthClientDTO.getClientId()), OauthClientDTO.class));
        } catch (Exception e) {
            log.error("查询方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 分页方法
     *
     * @param oauthClientDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    public ExecuteResult<HashMap<String, Object>> getPage(OauthClientDTO oauthClientDTO) {
        ExecuteResult<HashMap<String, Object>> result = new ExecuteResult<>();
        try {
            IPage<OauthClientEntity> page = oauthClientService.getPage(BeanCopyUtil.copy(oauthClientDTO, OauthClientEntity.class));
            result.setData(BeanCopyUtil.convertToMap(page, OauthClientDTO.class));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 新增方法
     *
     * @param oauthClientDTO 客户端信息
     * @return 客户端信息
     */
    @PostMapping(UrlConstants.INSERT)
    public ExecuteResult<OauthClientDTO> insert(@RequestBody OauthClientDTO oauthClientDTO, Principal principal) {
        ExecuteResult<OauthClientDTO> result = new ExecuteResult<>();
        try {
            check(oauthClientDTO);
            OauthClientEntity oauthClientEntity = BeanCopyUtil.copy(oauthClientDTO, OauthClientEntity.class);
            oauthClientEntity.setId(UuidUtil.getId());
            oauthClientEntity.setCreatedBy(principal.getName());
            oauthClientEntity.setCreatedDate(LocalDateTime.now());
            oauthClientService.insert(oauthClientEntity);
            result.setData(BeanCopyUtil.copy(oauthClientEntity, oauthClientDTO));
        } catch (Exception e) {
            log.error("插入方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param oauthClientDTO 客户端信息
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public ExecuteResult<String> update(@Valid @RequestBody OauthClientDTO oauthClientDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            check(oauthClientDTO);
            OauthClientEntity oauthClientEntity = BeanCopyUtil.copy(oauthClientDTO, OauthClientEntity.class);
            oauthClientEntity.setUpdatedBy(principal.getName());
            oauthClientEntity.setUpdatedDate(LocalDateTime.now());
            oauthClientService.updateByClientId(oauthClientEntity);
            result.setData("更新成功");
        } catch (Exception e) {
            log.error("更新方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 删除方法
     *
     * @param codes 客户端信息
     * @return 执行结果
     */
    @DeleteMapping(UrlConstants.DELETE)
    public ExecuteResult<String> delete(@RequestBody List<String> codes, Principal principal) {
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

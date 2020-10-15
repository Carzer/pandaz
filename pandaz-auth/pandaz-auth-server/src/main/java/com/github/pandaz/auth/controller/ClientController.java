package com.github.pandaz.auth.controller;


import com.github.pandaz.auth.dto.ClientDTO;
import com.github.pandaz.auth.entity.ClientEntity;
import com.github.pandaz.auth.service.ClientService;
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
 * oauth2客户端信息
 *
 * @author Carzer
 * @since 2020-01-02
 */
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Client", tags = "oauth2客户端信息")
@PreAuth("client")
public class ClientController extends BaseController<ClientDTO, ClientEntity> {

    /**
     * 客户端服务
     */
    private final ClientService clientService;

    /**
     * 获取服务方法
     *
     * @return 服务
     */
    @Override
    protected BaseService<ClientEntity> getBaseService() {
        return this.clientService;
    }

    /**
     * 检查方法
     *
     * @param clientDTO 客户端信息
     */
    @Override
    protected void check(ClientDTO clientDTO) {
        Assert.hasText(clientDTO.getClientId(), "clientId不能为空");
    }
}

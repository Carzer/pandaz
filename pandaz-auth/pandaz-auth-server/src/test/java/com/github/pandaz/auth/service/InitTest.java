package com.github.pandaz.auth.service;

import com.github.pandaz.auth.AuthServerApp;
import com.github.pandaz.auth.entity.ClientEntity;
import com.github.pandaz.auth.entity.GroupRoleEntity;
import com.github.pandaz.auth.entity.RoleEntity;
import com.github.pandaz.auth.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.assertThat;

/**
 * 初始化
 *
 * @author Carzer
 * @since 2020-03-30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthServerApp.class)
@Rollback
@Slf4j
public class InitTest {

    private ClientService clientService;

    private UserService userService;

    private RoleService roleService;

    private GroupRoleService groupRoleService;

    @BeforeClass
    public static void setUp() {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "logs/auth-test/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "logs/auth-test/nacos/naming");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOauthClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setGroupRoleService(GroupRoleService groupRoleService) {
        this.groupRoleService = groupRoleService;
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void insert() {
        int result = 0;
        try {
            insertUser();
            insertClient();
            insertRole();
            bindUserAndRole();
        } catch (Exception e) {
            log.error("初始化失败", e);
        }
        assertThat(result, anything());
    }

    private void insertUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setCode("admin");
        userEntity.setName("admin");
        userEntity.setLoginName("admin");
        userEntity.setPassword("admin");
        userEntity.setPhone("15000000000");
        userEntity.setExpireAt(LocalDateTime.now().plusYears(1L));
        userService.insert(userEntity);
    }

    private void insertClient() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId("test");
        clientEntity.setClientName("测试客户端");
        clientEntity.setClientSecret("test");
        clientEntity.setScope("read,write");
        clientEntity.setAuthorizedGrantTypes("password,refresh");
        clientEntity.setAccessTokenValidity(1800);
        clientEntity.setRefreshTokenValidity(1800);
        clientService.insert(clientEntity);
    }

    private void insertRole() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode("ROLE_ADMIN");
        roleEntity.setName("系统管理员");
        roleService.insert(roleEntity);
        RoleEntity role = new RoleEntity();
        role.setCode("ROLE_SUPER_ADMIN");
        role.setName("超级管理员");
        roleService.insert(roleEntity);
    }

    private void bindUserAndRole() {
        GroupRoleEntity groupRoleEntity = new GroupRoleEntity();
        groupRoleEntity.setRoleCode("ROLE_ADMIN");
        groupRoleEntity.setGroupCode("admin");
        groupRoleService.insert(groupRoleEntity);
    }

}

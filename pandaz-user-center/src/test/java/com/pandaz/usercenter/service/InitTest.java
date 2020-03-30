package com.pandaz.usercenter.service;

import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.GroupRoleEntity;
import com.pandaz.usercenter.entity.OauthClientEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import com.pandaz.usercenter.entity.UserEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author Carzer
 * @since 2020-03-30
 */
public class InitTest extends BasisUnitTest {

    private OauthClientService oauthClientService;

    private UserService userService;

    private RoleService roleService;

    private GroupRoleService groupRoleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOauthClientService(OauthClientService oauthClientService) {
        this.oauthClientService = oauthClientService;
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
    public void insert() {
        insertUser();
        insertClient();
        insertRole();
        bindUserAndRole();
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
        OauthClientEntity oauthClientEntity = new OauthClientEntity();
        oauthClientEntity.setClientId("test");
        oauthClientEntity.setClientSecret("test");
        oauthClientEntity.setScope("read,write");
        oauthClientEntity.setAuthorizedGrantTypes("password,refresh");
        oauthClientEntity.setAccessTokenValidity(1800);
        oauthClientEntity.setRefreshTokenValidity(1800);
        oauthClientService.insert(oauthClientEntity);
    }

    private void insertRole() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode("ROLE_ADMIN");
        roleEntity.setName("系统管理员");
        roleService.insert(roleEntity);
    }

    private void bindUserAndRole() {
        GroupRoleEntity groupRoleEntity = new GroupRoleEntity();
        groupRoleEntity.setRoleCode("ROLE_ADMIN");
        groupRoleEntity.setGroupCode("admin");
        groupRoleService.insert(groupRoleEntity);
    }

}
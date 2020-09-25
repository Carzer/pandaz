package com.github.pandaz.auth.custom;

import com.github.pandaz.auth.dto.SecurityUser;
import com.github.pandaz.auth.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

/**
 * 自定义授权管理器
 *
 * @author Carzer
 * @since 2019-10-24
 */
@Slf4j
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

    /**
     * 角色服务
     */
    private RoleService roleService;

    /**
     * 角色服务设置方法
     *
     * @param roleService 角色服务
     */
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 构造方法
     *
     * @param userDetailsService 用户信息服务
     * @param passwordEncoder    密码工具
     */
    public CustomDaoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this(userDetailsService, passwordEncoder, null);
    }

    /**
     * 构造方法
     *
     * @param userDetailsService         用户信息服务
     * @param passwordEncoder            密码工具
     * @param userDetailsPasswordService 修改密码服务
     */
    public CustomDaoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserDetailsPasswordService userDetailsPasswordService) {
        super();
        setUserDetailsService(userDetailsService);
        setPasswordEncoder(passwordEncoder);
        setUserDetailsPasswordService(userDetailsPasswordService);
    }

    /**
     * 验证通过后，查询权限等信息
     *
     * @param principal      principal
     * @param authentication authentication
     * @param user           user
     * @return org.springframework.security.core.Authentication
     */
    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        SecurityUser securityUser = (SecurityUser) user;
        //登陆成功后设置角色信息
        Set<GrantedAuthority> authorities = roleService.findBySecurityUser(securityUser);
        return super.createSuccessAuthentication(principal, authentication, new SecurityUser(user.getUsername(), user.getPassword(), authorities, securityUser.getUser()));
    }

    /**
     * 校验密码
     *
     * @param userDetails    userDetails
     * @param authentication authentication
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) {
        super.additionalAuthenticationChecks(userDetails, authentication);
        log.debug("密码校验通过");
    }
}

package com.pandaz.usercenter.custom.provider;

import com.pandaz.commons.dto.usercenter.UserDTO;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.RoleDetailEntity;
import com.pandaz.usercenter.custom.SecurityUser;
import com.pandaz.usercenter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * pandaz:com.pandaz.usercenter.custom.provider
 * <p>
 * 自定义授权管理器
 *
 * @author Carzer
 * @date 2019-10-24 13:59
 */
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

    /**
     * 角色mapper
     */
    @Autowired
    private RoleService roleService;

    /**
     * 构造方法
     *
     * @param userDetailsService 用户信息服务
     * @param passwordEncoder    密码工具
     * @author Carzer
     * @date 2019/10/24 14:50
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
     * @author Carzer
     * @date 2019/10/24 14:50
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
     * @author Carzer
     * @date 2019/10/24 14:51
     */
    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        SecurityUser securityUser = (SecurityUser) user;
        UserDTO userDTO = securityUser.getUser();
        String userCode = userDTO.getCode();
        /* 查询角色 */
        List<RoleDetailEntity> roleList = roleService.findByUserCode(userCode, SysConstants.IS_PUBLIC);
        //设置角色信息
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (!CollectionUtils.isEmpty(roleList)) {
            roleList.forEach(role ->
                    authorities.add(new SimpleGrantedAuthority(role.getCode())));
        }
        authorities.addAll(user.getAuthorities());
        return super.createSuccessAuthentication(principal, authentication, new SecurityUser(user.getUsername(), user.getPassword(), authorities, userDTO));
    }

    /**
     * 校验密码
     *
     * @param userDetails    userDetails
     * @param authentication authentication
     * @author Carzer
     * @date 2019/10/24 14:51
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) {
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}

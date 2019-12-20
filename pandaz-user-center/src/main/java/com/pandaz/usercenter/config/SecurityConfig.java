package com.pandaz.usercenter.config;

import com.pandaz.commons.dto.usercenter.UserDTO;
import com.pandaz.commons.util.BeanCopierUtil;
import com.pandaz.commons.util.CustomPasswordEncoder;
import com.pandaz.usercenter.custom.SecurityUser;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.custom.handler.AuthDeniedHandler;
import com.pandaz.usercenter.custom.handler.LoginFailureHandler;
import com.pandaz.usercenter.custom.handler.LoginSuccessHandler;
import com.pandaz.usercenter.custom.provider.CustomDaoAuthenticationProvider;
import com.pandaz.usercenter.entity.UserEntity;
import com.pandaz.usercenter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * spring security配置类
 *
 * @author Carzer
 * @since 2019-07-16
 */
@Configuration
@EnableWebSecurity
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1200)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 获取用户信息服务
     */
    private final UserService userService;

    /**
     * 获取登录用户的相关信息
     *
     * @param auth 权限管理器
     */
    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(customDaoAuthenticationProvider())
        ;
    }

    /**
     * 配置请求相关
     *
     * @param http http
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/token/**", "/sessionService/**", "/message/**").permitAll()
                .anyRequest()
                .authenticated().and()
//                .authenticated().and()
                .formLogin().permitAll()
                //登录成功后可使用loginSuccessHandler()存储用户信息，可选。
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailureHandler())
                .and()
                .logout().permitAll()//退出页面
                .and()
                .csrf().disable()
                .exceptionHandling().accessDeniedHandler(authDeniedHandler())
        //.httpBasic().disable()
//                .sessionManagement().maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
        ;
    }

    /**
     * 加密类
     *
     * @return org.springframework.security.crypto.password.PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    /**
     * 登录成功后执行的方法
     *
     * @return com.pandaz.usercenter.util.LoginSuccessHandler
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    /**
     * 登录失败执行的方法
     *
     * @return com.pandaz.usercenter.util.LoginFailureHandler
     */
    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    /**
     * 权限拒绝handler
     *
     * @return com.pandaz.usercenter.handler.AuthDeniedHandler
     */
    @Bean
    public AuthDeniedHandler authDeniedHandler() {
        return new AuthDeniedHandler();
    }


    /**
     * 返回一个实现UserDetailsService接口的类
     *
     * @return org.springframework.security.core.userdetails.UserDetailsService
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return loginName -> {
            UserEntity user = userService.loadUserByUsername(loginName);
            //用户不存在
            if (user == null) {
                String notFoundMsg = "账号[" + loginName + "]不存在。";
                throw new UsernameNotFoundException(notFoundMsg);
            }
            String username = user.getName();
            Byte locked = user.getLocked();
            //用户已锁定
            if (SysConstants.IS_LOCKED.equals(locked)) {
                throw new LockedException("用户[" + username + "]已锁定，请联系管理员。");
            }
            LocalDateTime expireAt = user.getExpireAt();
            //用户已过期
            if (expireAt == null || LocalDateTime.now().isAfter(expireAt)) {
                String expireTime = expireAt == null ? "" : "于" + expireAt.toString();
                String accountExpiredMsg = String.format("用户[%s]已%s过期。", username, expireTime);
                throw new AccountExpiredException(accountExpiredMsg);
            }
            UserDTO userDTO = BeanCopierUtil.copy(user, UserDTO.class);
            List<GrantedAuthority> authorities = new ArrayList<>();
            return new SecurityUser(username, user.getPassword(), authorities, userDTO);
        };
    }

    /**
     * 自定义授权管理器
     *
     * @return com.pandaz.usercenter.provider.CustomDaoAuthenticationProvider
     */
    @Bean
    public CustomDaoAuthenticationProvider customDaoAuthenticationProvider() {
        return new CustomDaoAuthenticationProvider(userDetailsService(), passwordEncoder());
    }
}

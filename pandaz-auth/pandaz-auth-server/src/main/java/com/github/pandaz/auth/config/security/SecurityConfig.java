package com.github.pandaz.auth.config.security;

import com.github.pandaz.auth.config.oauth.AuthorizationServerConfig;
import com.github.pandaz.auth.custom.CustomDaoAuthenticationProvider;
import com.github.pandaz.auth.custom.CustomProperties;
import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.auth.custom.filter.CaptchaAuthenticationFilter;
import com.github.pandaz.auth.custom.handler.CustomAuthDeniedHandler;
import com.github.pandaz.auth.custom.handler.CustomLogoutSuccessHandler;
import com.github.pandaz.auth.custom.handler.LoginFailureHandler;
import com.github.pandaz.auth.custom.handler.LoginSuccessHandler;
import com.github.pandaz.auth.custom.interceptor.CustomFilterSecurityInterceptor;
import com.github.pandaz.auth.dto.SecurityUser;
import com.github.pandaz.auth.dto.UserDTO;
import com.github.pandaz.auth.entity.UserEntity;
import com.github.pandaz.auth.service.UserService;
import com.github.pandaz.commons.util.BeanCopyUtil;
import com.github.pandaz.commons.util.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = SysConstants.DEFAULT_EXPIRE_SECONDS)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 通用配置
     */
    private final CustomProperties customProperties;

    /**
     * 获取用户信息服务
     */
    private final UserService userService;

    /**
     * 登陆失败
     */
    private final LoginFailureHandler loginFailureHandler;

    /**
     * 访问拒绝
     */
    private final CustomAuthDeniedHandler authDeniedHandler;

    /**
     * 登出成功
     */
    private final CustomLogoutSuccessHandler logoutSuccessHandler;

    /**
     * 权限filter
     */
    private final CustomFilterSecurityInterceptor filterSecurityInterceptor;

    /**
     * 验证码filter
     */
    private final CaptchaAuthenticationFilter captchaAuthenticationFilter;

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
     * 创建Bean而不是直接注入，是因为会造成循环依赖
     *
     * @return LoginSuccessHandler
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    /**
     * 自定义授权管理器
     *
     * @return CustomDaoAuthenticationProvider
     */
    @Bean
    public CustomDaoAuthenticationProvider customDaoAuthenticationProvider() {
        return new CustomDaoAuthenticationProvider(userDetailsService(), passwordEncoder());
    }

    /**
     * AuthenticationManager
     * <p>
     * 如果不声明，会导致授权服务器无AuthenticationManager，而password方式无法获取token
     * {@link AuthorizationServerConfig#configure(AuthorizationServerEndpointsConfigurer endpoints)}
     * {@link AuthorizationServerEndpointsConfigurer} #getDefaultTokenGranters
     *
     * @return AuthenticationManager
     * @throws Exception e
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 返回一个实现UserDetailsService接口的类
     *
     * @return UserDetailsService
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return loginName -> {
            UserEntity user = userService.loadUserByUsername(loginName);
            // 用户不存在
            if (user == null) {
                String notFoundMsg = String.format("账号[%s]不存在。", loginName);
                throw new UsernameNotFoundException(notFoundMsg);
            }
            Byte locked = user.getLocked();
            // 用户已锁定
            if (SysConstants.LOCKED.equals(locked)) {
                throw new LockedException(String.format("用户[%s]已锁定，请联系管理员。", loginName));
            }
            LocalDateTime expireAt = user.getExpireAt();
            // 用户已过期
            if (expireAt == null || LocalDateTime.now().isAfter(expireAt)) {
                String expireTime = expireAt == null ? "" : String.format("于%s", expireAt.toString());
                String accountExpiredMsg = String.format("用户[%s]已%s过期。", loginName, expireTime);
                throw new AccountExpiredException(accountExpiredMsg);
            }
            UserDTO userDTO = BeanCopyUtil.copy(user, UserDTO.class);
            List<GrantedAuthority> authorities = new ArrayList<>();
            return new SecurityUser(loginName, user.getPassword(), authorities, userDTO);
        };
    }

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
                .antMatchers(customProperties.getExcludedPaths()).permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin().permitAll()
                // 登录成功后执行的方法
                .successHandler(loginSuccessHandler())
                // 登录失败执行的方法
                .failureHandler(loginFailureHandler)
                .and()
                // 允许登出
                .logout().permitAll()
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                // 关闭禁止跨域
                .csrf().disable()
                // 访问拒绝时执行的方法
                .exceptionHandling().accessDeniedHandler(authDeniedHandler)
        // 当前用户只准登陆一次，后续的禁止登陆
//                .and().sessionManagement().maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
        ;
        http.addFilterBefore(filterSecurityInterceptor, FilterSecurityInterceptor.class);
        http.addFilterBefore(captchaAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

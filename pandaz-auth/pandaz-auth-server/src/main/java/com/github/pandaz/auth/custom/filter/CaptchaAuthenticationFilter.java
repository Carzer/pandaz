package com.github.pandaz.auth.custom.filter;

import com.github.pandaz.auth.custom.CustomProperties;
import com.github.pandaz.auth.service.CaptchaService;
import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.util.PrintWriterUtil;
import com.github.pandaz.commons.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码 filter
 *
 * @author Carzer
 * @since 2020-04-27
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CaptchaAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 是否开启验证码
     */
    private final CustomProperties customProperties;

    /**
     * 验证码服务
     */
    private final CaptchaService captchaService;

    /**
     * 请求匹配器
     */
    AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher("/login", "POST");

    /**
     * 过滤
     *
     * @param request     request
     * @param response    response
     * @param filterChain filterChain
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (customProperties.getCaptcha().isEnable() && requestMatcher.matches(request)) {
            String randomId = this.obtainRandomId(request);
            String captcha = this.obtainCaptcha(request);
            RCode rCode = captchaService.check(randomId, captcha);
            if (!RCode.SUCCESS.equals(rCode)) {
                PrintWriterUtil.write(response, R.fail(rCode.getMessage()));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 获取输入的验证码
     */
    private String obtainCaptcha(HttpServletRequest request) {
        return request.getParameter("captcha");
    }

    /**
     * 获取key值
     */
    private String obtainRandomId(HttpServletRequest request) {
        return request.getParameter("randomId");
    }
}
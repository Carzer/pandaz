package com.pandaz.auth.custom.filter;

import com.pandaz.commons.util.PrintWriterUtil;
import com.pandaz.commons.util.R;
import com.pandaz.auth.custom.CustomProperties;
import com.pandaz.auth.service.CaptchaService;
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
        if (customProperties.isEnableCaptcha() && requestMatcher.matches(request)) {
            String randomId = this.obtainGeneratedCaptcha(request);
            String captcha = this.obtainCaptcha(request);
            R<Boolean> r = captchaService.check(randomId, captcha);
            if (Boolean.FALSE.equals(r.getData())) {
                PrintWriterUtil.write(response, r);
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
    private String obtainGeneratedCaptcha(HttpServletRequest request) {
        return request.getParameter("randomId");
    }
}
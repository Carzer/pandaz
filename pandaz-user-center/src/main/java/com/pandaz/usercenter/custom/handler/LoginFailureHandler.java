package com.pandaz.usercenter.custom.handler;

import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.commons.util.PrintWriterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败handler
 *
 * @author Carzer
 * @since 2019-10-25
 */
@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    /**
     * 授权失败
     *
     * @param httpServletRequest  httpServletRequest
     * @param httpServletResponse httpServletResponse
     * @param e                   e
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        String errorMsg = e.getMessage();
        log.warn(errorMsg);
        ExecuteResult<String> result = new ExecuteResult<>();
        if (e instanceof BadCredentialsException) {
            errorMsg = "密码错误。";
        }
        result.setError(errorMsg);
        PrintWriterUtil.write(httpServletResponse, result);
    }
}

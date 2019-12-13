package com.pandaz.usercenter.custom.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandaz.commons.util.ExecuteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * pandaz:com.pandaz.usercenter.custom.handler
 * <p>
 * 登录失败handler
 *
 * @author Carzer
 * @date 2019-10-25 08:52
 */
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

    /**
     * 授权失败
     *
     * @param httpServletRequest  httpServletRequest
     * @param httpServletResponse httpServletResponse
     * @param e                   e
     * @author Carzer
     * @date 2019/10/25 13:28
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        String errorMsg = e.getMessage();
        log.warn(errorMsg);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ExecuteResult<String> result = new ExecuteResult<>();
        if(e instanceof BadCredentialsException){
            errorMsg = "密码错误。";
        }
        result.setError(errorMsg);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(result);
        try (PrintWriter out = httpServletResponse.getWriter()) {
            out.write(s);
            out.flush();
        }
    }
}

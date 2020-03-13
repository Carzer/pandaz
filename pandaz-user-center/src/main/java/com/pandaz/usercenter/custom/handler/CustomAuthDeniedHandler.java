package com.pandaz.usercenter.custom.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandaz.commons.util.ExecuteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 权限拒绝handler
 *
 * @author Carzer
 * @since 2019-10-25
 */
@Slf4j
@Component
public class CustomAuthDeniedHandler implements AccessDeniedHandler {

    /**
     * 权限不足
     *
     * @param httpServletRequest  httpServletRequest
     * @param httpServletResponse httpServletResponse
     * @param e                   e
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        log.warn(e.getMessage());
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ExecuteResult<String> result = new ExecuteResult<>();
        String error = "权限不足，请联系管理员!";
        result.setError(error);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(result);
        try (PrintWriter out = httpServletResponse.getWriter()) {
            out.write(s);
            out.flush();
        }
    }
}
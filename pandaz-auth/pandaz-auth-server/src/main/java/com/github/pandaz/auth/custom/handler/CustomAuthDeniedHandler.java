package com.github.pandaz.auth.custom.handler;

import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.util.PrintWriterUtil;
import com.github.pandaz.commons.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException {
        log.warn(e.getMessage());
        PrintWriterUtil.write(httpServletResponse, new R<String>(RCode.FORBIDDEN), HttpServletResponse.SC_FORBIDDEN);
    }
}
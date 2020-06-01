package com.github.pandaz.auth.custom.handler;

import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.util.PrintWriterUtil;
import com.github.pandaz.commons.util.R;
import lombok.extern.slf4j.Slf4j;
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
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException {
        log.warn(RCode.BAD_CREDENTIALS.getMessage());
        PrintWriterUtil.write(httpServletResponse, new R<String>(RCode.BAD_CREDENTIALS));
    }
}

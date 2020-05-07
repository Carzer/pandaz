package com.pandaz.auth.custom.handler;

import com.pandaz.commons.util.R;
import com.pandaz.commons.util.PrintWriterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LogoutSuccessHandler
 *
 * @author Carzer
 * @since 2020-03-13
 */
@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * 登出成功
     *
     * @param request        request
     * @param response       response
     * @param authentication authentication
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 输出登出提示信息
        PrintWriterUtil.write(response, R.success());
    }
}

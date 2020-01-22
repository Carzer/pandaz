package com.pandaz.usercenter.custom.handler;

import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.commons.util.IpUtil;
import com.pandaz.commons.util.PrintWriterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LoginSuccessHandler
 *
 * @author Carzer
 * @since 2019-08-22
 */
@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * 登录成功后执行
     *
     * @param httpServletRequest  request
     * @param httpServletResponse response
     * @param authentication      authentication
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
            throws IOException {

        //输出登录提示信息
        log.debug("用户：[{}]登录", authentication.getName());
        log.debug("IP :{}", IpUtil.getIpAddress(httpServletRequest));

        ExecuteResult<Authentication> result = new ExecuteResult<>();
        result.setData(authentication);
        PrintWriterUtil.write(httpServletResponse, result);
    }
}

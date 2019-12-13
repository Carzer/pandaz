package com.pandaz.usercenter.custom.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.commons.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * pandaz:com.pandaz.usercenter.custom.handler
 * <p>
 * LoginSuccessHandler
 *
 * @author Carzer
 * @date 2019-08-22 09:09
 */
@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * 登录成功后执行
     *
     * @param httpServletRequest  request
     * @param httpServletResponse response
     * @param authentication      authentication
     * @author Carzer
     * Date 2019-08-22 09:09
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
            throws IOException {

        //输出登录提示信息
        log.debug("用户：[{}]登录", authentication.getName());
        log.debug("IP :{}", IpUtil.getIpAddress(httpServletRequest));

        httpServletResponse.setContentType("application/json;charset=utf-8");
        ExecuteResult<Authentication> result = new ExecuteResult<>();
        result.setData(authentication);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(result);
        try (PrintWriter out = httpServletResponse.getWriter()) {
            out.write(s);
            out.flush();
        }
//        super.onAuthenticationSuccess(request, response, authentication);
    }
}

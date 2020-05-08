package com.github.pandaz.auth.custom.handler;

import com.github.pandaz.commons.util.IpUtil;
import com.github.pandaz.commons.util.PrintWriterUtil;
import com.github.pandaz.commons.util.R;
import com.github.pandaz.auth.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * LoginSuccessHandler
 *
 * @author Carzer
 * @since 2019-08-22
 */
@Slf4j
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * token工具
     */
    private TokenUtil tokenUtil;

    /**
     * set方法
     *
     * @param tokenUtil tokenUtil
     */
    @Autowired
    public void setTokenUtil(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

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

        // 输出登录提示信息
        log.debug("用户：[{}]登录", authentication.getName());
        log.debug("IP :{}", IpUtil.getIpAddress(httpServletRequest));
        HashMap<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("authorities", authentication.getAuthorities());
        resultMap.put("user", authentication.getPrincipal());
        // 登陆成功后，返回用户信息的同时返回token
        resultMap.putAll(tokenUtil.generateToken(authentication));
        R<HashMap<String, Object>> r = new R<>(resultMap);
        PrintWriterUtil.write(httpServletResponse, r);
    }
}

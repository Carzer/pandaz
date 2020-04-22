package com.pandaz.usercenter.util;

import com.pandaz.commons.custom.SecurityUser;
import com.pandaz.commons.dto.usercenter.UserDTO;
import com.pandaz.commons.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * 用户工具类
 *
 * @author Carzer
 * @since 2019-09-03 14:37
 */
@Component
@Slf4j
public final class UserUtil {

    /**
     * 构造方法
     */
    private UserUtil() {

    }

    /**
     * 由security上下文环境中获取用户信息
     *
     * @param principal principal
     * @return 当前用户
     */
    public static Result<UserDTO> getUserFromPrincipal(Principal principal) {
        Result<UserDTO> result = new Result<>();
        try {
            if (principal == null) {
                result.setError("用户未登陆！");
                return result;
            }
            UserDTO user = ((SecurityUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser();
            result.setData(user);
        } catch (Exception e) {
            log.error("获取用户失败:", e);
            result.setError(e.getMessage());
        }
        return result;
    }
}

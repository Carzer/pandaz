package com.pandaz.usercenter.util;

import com.pandaz.commons.dto.usercenter.UserDTO;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.usercenter.custom.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * pandaz:com.pandaz.usercenter.util
 * <p>
 * 用户工具类
 *
 * @author Carzer
 * @date 2019-09-03 14:37
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
     * @return com.pandaz.commons.util.ExecuteResult<com.pandaz.usercenter.dto.UserDTO>
     * @author Carzer
     * @date 2019/9/3 14:41
     */
    public static ExecuteResult<UserDTO> getUserFromPrincipal(Principal principal) {
        ExecuteResult<UserDTO> result = new ExecuteResult<>();
        try {
            if (principal == null) {
                result.setError("用户未登陆！");
                return result;
            }
            UserDTO sysUser = ((SecurityUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser();
            result.setData(sysUser);
        } catch (Exception e) {
            log.error("获取用户失败:", e);
            result.setError(e.getMessage());
        }
        return result;
    }
}

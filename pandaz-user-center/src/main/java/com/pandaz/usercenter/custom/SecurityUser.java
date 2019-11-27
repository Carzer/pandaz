package com.pandaz.usercenter.custom;

import com.pandaz.usercenter.dto.UserDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * pandaz:com.pandaz.usercenter.custom
 * <p>
 * 安全用户类
 *
 * @author Carzer
 * Date: 2019-07-17 10:24
 */
@EqualsAndHashCode(callSuper = false)
public class SecurityUser extends User {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 用户
     */
    @Setter
    @Getter
    private UserDTO user;

    /**
     * 构造方法
     *
     * @param username    用户名
     * @param password    密码
     * @param authorities 权限
     * @param user        私有用户
     * @author Carzer
     * Date: 2019/10/23 16:53
     */
    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UserDTO user) {
        super(username, password, true, true, true, true, authorities);
        this.user = user;
    }
}

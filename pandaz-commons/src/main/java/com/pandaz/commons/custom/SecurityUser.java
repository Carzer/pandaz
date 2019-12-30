package com.pandaz.commons.custom;

import com.pandaz.commons.dto.usercenter.UserDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 安全用户类
 *
 * @author Carzer
 * @since 2019-07-17
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
     */
    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UserDTO user) {
        super(username, password, true, true, true, true, authorities);
        this.user = user;
    }
}

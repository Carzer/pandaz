package com.pandaz.commons.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * pandaz:com.pandaz.commons.util
 * <p>
 * 加密类
 *
 * @author Carzer
 * Date: 2019-07-16
 */
@Slf4j
public final class CustomPasswordEncoder extends BCryptPasswordEncoder {

    /**
     * 构造方法
     */
    public CustomPasswordEncoder() {
        super();
    }

    /**
     * 加密方法
     *
     * @param rawPassword 原始密码
     * @return java.lang.String
     * @author Carzer
     * Date: 2019-07-16 14:43
     */
    @Override
    public String encode(CharSequence rawPassword) {
        log.debug("rawPassword is {}", rawPassword);
        return super.encode(rawPassword);
    }

    /**
     * 是否匹配
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return boolean
     * @author Carzer
     * Date: 2019-07-16 14:43
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        log.debug("rawPassword is {}", rawPassword);
        return super.matches(rawPassword, encodedPassword);
    }
}

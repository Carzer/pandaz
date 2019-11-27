package com.pandaz.commons.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * pandaz:com.pandaz.commons.util
 * <p>
 * UUID工具类
 *
 * @author Carzer
 * @date 2019-08-22
 */
@Component
public final class UuidUtil {

    /**
     * 构造方法
     */
    private UuidUtil() {

    }

    /**
     * 生成36位uuid
     *
     * @return uuid
     * @author Carzer
     * @date 2019-08-22
     */
    public static String getUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成32位uuid
     *
     * @return uuid
     * @author Carzer
     * @date 2019-08-22
     */
    public static String getUnsignedUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll(".-", "");
    }
}

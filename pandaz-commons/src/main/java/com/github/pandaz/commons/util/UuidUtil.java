package com.github.pandaz.commons.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author Carzer
 * @since 2019-08-22
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
     */
    public static String getUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成32位uuid
     *
     * @return uuid
     */
    public static String getId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll(".-", "");
    }

}

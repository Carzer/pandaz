package com.github.pandaz.redis.config.redis;

import java.util.Optional;

/**
 * redis数据源切换
 *
 * @author Carzer
 * @since 2020-07-27
 */
public class RedisSwitchHolder {

    /**
     * 私有构造方法
     */
    private RedisSwitchHolder() {

    }

    /**
     * 本地线程共享对象
     */
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();


    /**
     * put方法
     *
     * @param name redis操作类名
     */
    public static void setTargetName(String name) {
        THREAD_LOCAL.set(name);
    }

    /**
     * 获取数据源
     *
     * @return redis操作类名
     */
    public static String getTargetName() {
        return Optional.ofNullable(THREAD_LOCAL.get()).orElse("");
    }

    /**
     * 清除redis操作类名
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}

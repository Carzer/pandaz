package com.github.pandaz.commons.aspects.tenant;

/**
 * 租户忽略holder
 *
 * @author Carzer
 * @since 2020-09-16
 */
public class SqlParserIgnoreHolder {

    private SqlParserIgnoreHolder() {

    }

    /**
     * 本地线程共享对象
     */
    private static final ThreadLocal<Integer> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * set方法
     *
     * @param ignoreScope 忽略列表
     */
    public static void setIgnoreScope(Integer ignoreScope) {
        THREAD_LOCAL.set(ignoreScope);
    }

    /**
     * 获取忽略列表
     *
     * @return 忽略列表
     */
    public static Integer getIgnoreScope() {
        return THREAD_LOCAL.get();
    }

    /**
     * 清除忽略列表
     */
    public static void removeIgnoreScope() {
        THREAD_LOCAL.remove();
    }
}

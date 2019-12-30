package com.pandaz.usercenter.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源持有者，负责利用ThreadLocal存取数据源名称
 *
 * @author Carzer
 * @since 2019-12-13
 * @see AbstractRoutingDataSource
 */
public class DynamicDataSourceHolder {

    /**
     * 私有构造方法
     */
    private DynamicDataSourceHolder() {

    }

    /**
     * 本地线程共享对象
     */
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * put方法
     *
     * @param name 数据源逻辑名称
     */
    public static void putDataSource(String name) {
        THREAD_LOCAL.set(name);
    }

    /**
     * 获取数据源
     * {@link DynamicDataSource#determineCurrentLookupKey}
     *
     * @return 数据源逻辑名称
     */
    public static String getDataSource() {
        return THREAD_LOCAL.get();
    }

    /**
     * 清除数据源名称
     */
    public static void removeDataSource() {
        THREAD_LOCAL.remove();
    }
}
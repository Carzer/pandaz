package com.pandaz.usercenter.config.datasource;

/**
 * Description: 动态数据源持有者，负责利用ThreadLocal存取数据源名称
 *
 * @author carzer
 * @date 2019/12/13
 */
public class DynamicDataSourceHolder {

    /**
     * 私有构造方法
     */
    private DynamicDataSourceHolder(){

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
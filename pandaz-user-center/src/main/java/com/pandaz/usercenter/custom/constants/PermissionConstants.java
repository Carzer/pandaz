package com.pandaz.usercenter.custom.constants;

/**
 * 权限常量
 *
 * @author Carzer
 * @since 2019-11-05
 */
public class PermissionConstants {

    /**
     * 私有构造方法
     */
    private PermissionConstants(){
    }

    /**
     * get请求方式
     */
    public static final Byte PERMISSION_GET = 1;

    /**
     * post请求方式
     */
    public static final Byte PERMISSION_POST = 2;

    /**
     * put请求方式
     */
    public static final Byte PERMISSION_PUT = 3;

    /**
     * delete请求方式
     */
    public static final Byte PERMISSION_DELETE = 4;
}

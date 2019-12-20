package com.pandaz.usercenter.custom.constants;

/**
 * 用户中心常量
 *
 * @author Carzer
 * @since 2019-10-25
 */
public final class SysConstants {

    /**
     * 构造方法
     */
    private SysConstants() {

    }

    /**
     * 组通用前缀
     */
    public static final String GROUP_PREFIX = "GROUP_";

    /**
     * 角色通用前缀
     */
    public static final String ROLE_PREFIX = "ROLE_";

    /**
     * 私有组后缀
     */
    public static final String PRIVATE_GROUP = "_PRIVATE_GROUP";

    /**
     * 私有角色后缀
     */
    public static final String PRIVATE_ROLE = "_PRIVATE_ROLE";

    /**
     * 密码不可为空提示
     */
    public static final String PD_NOT_NULL_WARN = "密码不可为空!";

    /**
     * 私有标记
     */
    public static final Byte IS_PRIVATE = 1;

    /**
     * 私有标记
     */
    public static final Byte IS_PUBLIC = 0;

    /**
     * 已锁定
     */
    public static final Byte IS_LOCKED = 1;

    /**
     * 图片验证码存于session中的key
     */
    public static final String SESSION_KEY_IMAGE_CODE = "SESSION_KEY_IMAGE_CODE";
}

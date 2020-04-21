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
     * 创建时间列
     */
    public static final String CREATED_DATE_COLUMN = "created_date";

    /**
     * 默认密码为12345678
     */
    public static final String DEFAULT_ENCODED_PASS = "$2a$10$89UJRZ6A.ubPmT9MrN6iEePGKdmW2N2b8tIe3Ng1MAVaTfRB2gTKC";

    /**
     * 私有标记
     */
    public static final Byte PRIVATE = 1;

    /**
     * 私有标记
     */
    public static final Byte PUBLIC = 0;

    /**
     * 已锁定
     */
    public static final Byte LOCKED = 1;

    /**
     * 二进制权限之和
     */
    public static final Integer TOTAL_DIGIT_RESULT = 67108863;

    /**
     * 最大位移数
     */
    public static final Byte MAX_DIGIT = 25;

    /**
     * 最小位移数
     */
    public static final Byte MIN_DIGIT = 1;

    /**
     * 图片验证码存于session中的key
     */
    public static final String SESSION_KEY_IMAGE_CODE = "SESSION_KEY_IMAGE_CODE";

    /**
     * 默认系统编码os_code
     */
    public static final String DEFAULT_SYS_CODE = "portal";

    /**
     * 默认session、token过期时间为60分钟
     */
    public static final int DEFAULT_EXPIRE_SECONDS = 60 * 60;
}

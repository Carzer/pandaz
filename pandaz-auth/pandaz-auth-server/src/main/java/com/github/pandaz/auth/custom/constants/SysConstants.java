package com.github.pandaz.auth.custom.constants;

/**
 * 授权中心常量
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
     * 二进制权限之和(1<<0 + 1<<1 + ... + 1<<25)
     */
    public static final Integer TOTAL_DIGIT_RESULT = 67108863;

    /**
     * 基础权限
     * 菜单有任一权限，都会在查询后附上基础权限，供前端固定使用
     */
    public static final Integer BASIC_DIGIT_RESULT = 1;

    /**
     * 最大位移数
     */
    public static final Byte MAX_DIGIT = 25;

    /**
     * 最小位移数
     */
    public static final Byte MIN_DIGIT = 0;

    /**
     * 默认session、token过期时间为60分钟
     */
    public static final int DEFAULT_EXPIRE_SECONDS = 60 * 60;

    /**
     * token中租户ID字段
     */
    public static final String TOKEN_TENANT_ID = "tenantId";
}

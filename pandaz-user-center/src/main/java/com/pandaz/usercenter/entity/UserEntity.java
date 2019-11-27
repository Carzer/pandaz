package com.pandaz.usercenter.entity;

import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * pandaz:com.pandaz.usercenter.entity
 * <p>
 * 用户信息
 *
 * @author Carzer
 * Date: 2019-10-23 10:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends BaseEntity<UserEntity> {

    private static final long serialVersionUID = 2983929245005559740L;
    /**
     * 用户名
     */
    private String name;

    /**
     * 用户编码
     */
    private String code;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private Byte gender;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 是否锁定
     */
    private Byte locked;

    /**
     * 过期时间
     */
    private Timestamp expireAt;

}
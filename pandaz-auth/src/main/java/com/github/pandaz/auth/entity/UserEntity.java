package com.github.pandaz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author Carzer
 * @since 2019-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_user")
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = 2983929245005559740L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 用户编码
     */
    @TableField("code")
    private String code;

    /**
     * 登录名
     */
    @TableField("login_name")
    private String loginName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 性别(0:男，1:女)
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 用户类型
     */
    @TableField("user_type")
    private String userType;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 电话号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 是否锁定
     */
    @TableField("locked")
    private Byte locked;

    /**
     * 过期时间
     */
    @TableField("expire_at")
    private LocalDateTime expireAt;

    /**
     * 是否过期的条件查询
     */
    @TableField(exist = false)
    private String expireState;

}
package com.pandaz.usercenter.dto;

import com.pandaz.commons.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * pandaz:com.pandaz.usercenter.dto
 * <p>
 * 用户数据传输类
 *
 * @author Carzer
 * Date: 2019-07-17 13:10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends BaseDTO {

    private static final long serialVersionUID = -3569077309970490430L;

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

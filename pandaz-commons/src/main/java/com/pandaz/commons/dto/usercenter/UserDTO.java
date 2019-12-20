package com.pandaz.commons.dto.usercenter;

import com.pandaz.commons.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * 用户数据传输类
 *
 * @author Carzer
 * @since 2019-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends BaseDTO {

    private static final long serialVersionUID = -3569077309970490430L;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户编码
     */
    @NotEmpty
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
    private LocalDateTime expireAt;

}

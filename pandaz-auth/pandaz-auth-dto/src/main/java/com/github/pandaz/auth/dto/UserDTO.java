package com.github.pandaz.auth.dto;

import com.github.pandaz.commons.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author Carzer
 * @since 2019-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserDTO", description = "用户信息")
public class UserDTO extends BaseDTO {

    private static final long serialVersionUID = -3569077309970490430L;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @Size(min = 1, max = 50)
    private String name;

    /**
     * 用户编码
     */
    @ApiModelProperty("用户编码")
    @NotEmpty
    @Size(min = 1, max = 36)
    private String code;

    /**
     * 登录名
     */
    @ApiModelProperty("登录名")
    @Size(min = 1, max = 50)
    private String loginName;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    @Min(0)
    @Max(1)
    private Integer gender;

    /**
     * 用户类型
     */
    @ApiModelProperty("用户类型")
    private String userType;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @Email
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @Size(min = 11, max = 20)
    private String phone;

    /**
     * 是否锁定
     */
    @ApiModelProperty("是否锁定")
    @Min(0)
    @Max(1)
    private Byte locked;

    /**
     * 过期时间
     */
    @ApiModelProperty("过期时间")
    private LocalDateTime expireAt;

    /**
     * 是否过期的条件查询
     */
    @ApiModelProperty("是否过期的条件查询")
    private String expireState;
}

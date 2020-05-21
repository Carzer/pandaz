package com.github.pandaz.commons.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户-密码
 *
 * @author Carzer
 * @since 2019-10-29
 */
@Data
@ApiModel(value = "UserPwdDTO", description = "用户-密码")
public class UserPwdDTO implements Serializable {

    private static final long serialVersionUID = 7163488819529250209L;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 用户信息
     */
    @NotNull
    @ApiModelProperty("用户信息")
    private UserDTO userDTO;
}

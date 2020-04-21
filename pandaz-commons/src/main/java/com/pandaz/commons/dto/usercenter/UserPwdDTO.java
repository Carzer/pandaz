package com.pandaz.commons.dto.usercenter;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户更新密码DTO
 *
 * @author Carzer
 * @since 2019-10-29
 */
@Data
public class UserPwdDTO implements Serializable {

    private static final long serialVersionUID = 7163488819529250209L;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户信息
     */
    @NotNull
    private UserDTO userDTO;
}

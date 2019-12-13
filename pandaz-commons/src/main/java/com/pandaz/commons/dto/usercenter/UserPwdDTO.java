package com.pandaz.commons.dto.usercenter;

import lombok.Data;

import java.io.Serializable;

/**
 * pandaz:com.pandaz.commons.dto.usercenter
 * <p>
 * 用户更新密码DTO
 *
 * @author Carzer
 * @date 2019-10-29 15:48
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
    private UserDTO userDTO;
}

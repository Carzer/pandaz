package com.pandaz.usercenter.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * pandaz:com.pandaz.usercenter.dto
 * <p>
 * 用户更新密码DTO
 *
 * @author Carzer
 * Date: 2019-10-29 15:48
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

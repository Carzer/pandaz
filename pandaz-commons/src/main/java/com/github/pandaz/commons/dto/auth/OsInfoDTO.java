package com.github.pandaz.commons.dto.auth;

import com.github.pandaz.commons.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 系统信息DTO
 *
 * @author Carzer
 * @since 2019-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OsInfoDTO extends BaseDTO {

    private static final long serialVersionUID = 1210965567272983588L;

    /**
     * 主键
     */
    private String id;

    /**
     * 系统名
     */
    private String name;

    /**
     * 系统编码
     */
    @NotEmpty
    private String code;

    /**
     * 父编码
     */
    private String parentCode;

    /**
     * 是否锁定
     */
    private Byte locked;
}
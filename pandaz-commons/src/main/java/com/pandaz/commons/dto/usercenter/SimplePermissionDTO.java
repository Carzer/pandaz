package com.pandaz.commons.dto.usercenter;

import com.pandaz.commons.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 权限信息DTO
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SimplePermissionDTO extends BaseDTO {

    private static final long serialVersionUID = 1263140232148096323L;

    /**
     * 权限编码
     */
    @NotEmpty
    private String code;

    /**
     * 系统编码
     */
    private String osCode;

    /**
     * 菜单编码
     */
    private String menuCode;

}

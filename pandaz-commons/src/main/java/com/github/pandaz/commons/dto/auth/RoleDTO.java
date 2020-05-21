package com.github.pandaz.commons.dto.auth;

import com.github.pandaz.commons.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 角色信息
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RoleDTO", description = "角色信息")
public class RoleDTO extends BaseDTO {

    private static final long serialVersionUID = -1133364210045318135L;

    /**
     * 角色名
     */
    @ApiModelProperty("角色名")
    private String name;

    /**
     * 角色编码
     */
    @NotEmpty
    @ApiModelProperty("角色编码")
    private String code;

    /**
     * 父角色编码
     */
    @ApiModelProperty("父角色编码")
    private String parentCode;

    /**
     * 是否私有(0:否，1:是)
     */
    @ApiModelProperty("是否私有(0:否，1:是)")
    private Byte isPrivate;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    @ApiModelProperty("是否锁定(0:未锁定，1:已锁定)")
    private Byte locked;
}

package com.github.pandaz.commons.dto.auth;

import com.github.pandaz.commons.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 组-角色
 *
 * @author Carzer
 * @since 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "GroupRoleDTO", description = "组-角色")
public class GroupRoleDTO extends BaseDTO {

    private static final long serialVersionUID = 384141665728196551L;

    /**
     * 组编码
     */
    @ApiModelProperty("组编码")
    private String groupCode;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String roleCode;

    /**
     * 组编码list
     */
    @ApiModelProperty("组编码list")
    private List<String> groupCodes;

    /**
     * 角色编码list
     */
    @ApiModelProperty("角色编码list")
    private List<String> roleCodes;
}

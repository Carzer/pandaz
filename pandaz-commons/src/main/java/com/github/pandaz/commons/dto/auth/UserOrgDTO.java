package com.github.pandaz.commons.dto.auth;

import com.github.pandaz.commons.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户-组织
 *
 * @author Carzer
 * @since 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserOrgDTO", description = "用户-组织")
public class UserOrgDTO extends BaseDTO {

    private static final long serialVersionUID = -580174751221339516L;

    /**
     * 用户编码
     */
    @ApiModelProperty("用户编码")
    private String userCode;

    /**
     * 组织编码
     */
    @ApiModelProperty("组织编码")
    private String orgCode;

    /**
     * 用户编码列表
     */
    @ApiModelProperty("用户编码列表")
    private List<String> userCodes;

    /**
     * 组编码列表
     */
    @ApiModelProperty("组编码列表")
    private List<String> orgCodes;
}

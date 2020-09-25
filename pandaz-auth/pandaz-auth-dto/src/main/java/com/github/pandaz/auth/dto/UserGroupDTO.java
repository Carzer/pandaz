package com.github.pandaz.auth.dto;

import com.github.pandaz.commons.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户-组
 *
 * @author Carzer
 * @since 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserGroupDTO", description = "用户-组")
public class UserGroupDTO extends BaseDTO {

    private static final long serialVersionUID = 2788925334125259927L;

    /**
     * 用户编码
     */
    @ApiModelProperty("用户编码")
    private String userCode;

    /**
     * 组编码
     */
    @ApiModelProperty("组编码")
    private String groupCode;

    /**
     * 用户编码列表
     */
    @ApiModelProperty("用户编码列表")
    private List<String> userCodes;

    /**
     * 组编码列表
     */
    @ApiModelProperty("组编码列表")
    private List<String> groupCodes;
}

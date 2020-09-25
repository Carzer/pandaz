package com.github.pandaz.auth.dto;

import com.github.pandaz.commons.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 系统信息
 *
 * @author Carzer
 * @since 2019-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OsInfoDTO", description = "系统信息")
public class OsInfoDTO extends BaseDTO {

    private static final long serialVersionUID = 1210965567272983588L;

    /**
     * 系统名
     */
    @ApiModelProperty("系统名")
    @Size(min = 1, max = 100)
    private String name;

    /**
     * 系统编码
     */
    @NotEmpty
    @ApiModelProperty("系统编码")
    @Size(min = 1, max = 50)
    private String code;

    /**
     * 父编码
     */
    @ApiModelProperty("父编码")
    @Size(max = 50)
    private String parentCode;

    /**
     * 是否锁定
     */
    @ApiModelProperty("是否锁定")
    @Min(0)
    @Max(1)
    private Byte locked;
}
package com.github.pandaz.commons.dto.auth;

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
 * 组信息
 *
 * @author Carzer
 * @since 2019-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "GroupDTO", description = "组信息")
public class GroupDTO extends BaseDTO {

    private static final long serialVersionUID = -5509967734797410481L;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @Size(min = 1, max = 100)
    private String name;

    /**
     * 组编码
     */
    @NotEmpty
    @ApiModelProperty("组编码")
    @Size(min = 1, max = 42)
    private String code;

    /**
     * 父级组编码
     */
    @ApiModelProperty("父级组编码")
    @Size(min = 1, max = 42)
    private String parentCode;

    /**
     * 是否私有(0:否，1:是)
     */
    @ApiModelProperty("是否私有(0:否，1:是)")
    @Min(0)
    @Max(1)
    private Byte isPrivate;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    @ApiModelProperty("是否锁定(0:未锁定，1:已锁定)")
    @Min(0)
    @Max(1)
    private Byte locked;
}

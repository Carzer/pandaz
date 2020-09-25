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
 * 字典信息
 *
 * @author Carzer
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DictInfoDTO", description = "字典信息")
public class DictInfoDTO extends BaseDTO {

    private static final long serialVersionUID = -8789242292201236925L;

    /**
     * 类型名称
     */
    @ApiModelProperty("类型名称")
    @Size(min = 1, max = 100)
    private String name;

    /**
     * 类型编码
     */
    @ApiModelProperty("类型编码")
    @NotEmpty
    @Size(min = 1, max = 50)
    private String code;

    /**
     * 字典类型编码
     */
    @ApiModelProperty("字典类型编码")
    @Size(min = 1, max = 50)
    private String typeCode;

    /**
     * 字典类型名称
     */
    @ApiModelProperty("字典类型名称")
    @Size(min = 1, max = 100)
    private String typeName;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    @ApiModelProperty("是否锁定(0:未锁定，1:已锁定)")
    @Min(0)
    @Max(1)
    private Byte locked;
}

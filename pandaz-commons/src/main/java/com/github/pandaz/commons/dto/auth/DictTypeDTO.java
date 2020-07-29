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
 * 字典类型
 *
 * @author Carzer
 * @since 2019-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DictTypeDTO", description = "字典类型")
public class DictTypeDTO extends BaseDTO {

    private static final long serialVersionUID = -8875582609243206625L;

    /**
     * 类型名称
     */
    @NotEmpty
    @ApiModelProperty("类型名称")
    @Size(min = 1, max = 100)
    private String name;

    /**
     * 类型编码
     */
    @NotEmpty
    @ApiModelProperty("类型编码")
    @Size(min = 1, max = 50)
    private String code;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    @ApiModelProperty("是否锁定(0:未锁定，1:已锁定)")
    @Min(0)
    @Max(1)
    private Byte locked;
}

package com.pandaz.commons.dto.usercenter;

import com.pandaz.commons.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 字典类型DTO
 *
 * @author Carzer
 * @since 2019-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictTypeDTO extends BaseDTO {

    private static final long serialVersionUID = -8875582609243206625L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 类型名称
     */
    @NotEmpty
    private String name;

    /**
     * 类型编码
     */
    @NotEmpty
    private String code;

    /**
     * 是否锁定
     */
    private Byte locked;

}

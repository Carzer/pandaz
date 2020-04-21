package com.pandaz.commons.dto.usercenter;

import com.pandaz.commons.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典信息DTO
 *
 * @author Carzer
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictInfoDTO extends BaseDTO {

    private static final long serialVersionUID = -8789242292201236925L;

    /**
     * 主键
     */
    private String id;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 类型编码
     */
    private String code;

    /**
     * 字典类型编码
     */
    private String typeCode;

    /**
     * 字典类型名称
     */
    private String typeName;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    private Byte locked;
}

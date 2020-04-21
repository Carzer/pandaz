package com.pandaz.commons.dto.usercenter;

import com.pandaz.commons.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 组信息DTO
 *
 * @author Carzer
 * @since 2019-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupDTO extends BaseDTO {

    private static final long serialVersionUID = -5509967734797410481L;

    /**
     * 主键
     */
    private String id;

    /**
     * 组名
     */
    private String name;

    /**
     * 组编码
     */
    @NotEmpty
    private String code;

    /**
     * 父级组编码
     */
    private String parentCode;

    /**
     * 是否私有(0:否，1:是)
     */
    private Byte isPrivate;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    private Byte locked;
}

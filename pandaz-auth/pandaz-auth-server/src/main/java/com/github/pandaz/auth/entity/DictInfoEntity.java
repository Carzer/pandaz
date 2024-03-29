package com.github.pandaz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字典信息表
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_dict_info")
public class DictInfoEntity extends BaseEntity {

    private static final long serialVersionUID = 7397279952975046824L;

    /**
     * 类型名称
     */
    @TableField("name")
    private String name;

    /**
     * 类型编码
     */
    @TableField("code")
    private String code;

    /**
     * 字典类型编码
     */
    @TableField("type_code")
    private String typeCode;

    /**
     * 字典类型名称
     */
    @TableField(exist = false)
    private String typeName;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    @TableField("locked")
    private Byte locked;
}

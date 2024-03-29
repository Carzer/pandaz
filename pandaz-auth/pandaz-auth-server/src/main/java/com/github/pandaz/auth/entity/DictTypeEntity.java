package com.github.pandaz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字典类型表
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("auth_dict_type")
public class DictTypeEntity extends BaseEntity {

    private static final long serialVersionUID = -4804489435289983563L;

    /**
     * 类型名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 类型编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    @TableField("locked")
    private Byte locked;
}

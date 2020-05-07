package com.pandaz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pandaz.commons.entity.BaseEntity;
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
@TableName("t_sys_dict_type")
public class DictTypeEntity extends BaseEntity {

    private static final long serialVersionUID = -4804489435289983563L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;

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

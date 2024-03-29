package com.github.pandaz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户-组关系
 *
 * @author Carzer
 * @since 2019-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("auth_user_group")
public class UserGroupEntity extends BaseEntity {

    private static final long serialVersionUID = 6268647528238681429L;

    /**
     * 用户编码
     */
    @TableField("user_code")
    private String userCode;

    /**
     * 组编码
     */
    @TableField("group_code")
    private String groupCode;

    /**
     * 是否私有(0:否，1:是)
     */
    @TableField("is_private")
    private Byte isPrivate;

    @TableField(exist = false)
    private Integer version;

    /**
     * 用户编码列表
     */
    @TableField(exist = false)
    private List<String> userCodes;

    /**
     * 组编码列表
     */
    @TableField(exist = false)
    private List<String> groupCodes;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;
}
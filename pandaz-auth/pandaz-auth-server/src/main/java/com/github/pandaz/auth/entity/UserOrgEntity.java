package com.github.pandaz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户-组织
 *
 * @author Carzer
 * @since 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_user_org")
public class UserOrgEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编码
     */
    @TableField("user_code")
    private String userCode;

    /**
     * 组织编码
     */
    @TableField("org_code")
    private String orgCode;

    /**
     * 版本
     */
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
    private List<String> orgCodes;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;
}

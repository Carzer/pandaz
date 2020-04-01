package com.pandaz.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * oauth2客户端信息
 * </p>
 *
 * @author Carzer
 * @since 2020-01-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_sys_oauth_client")
public class OauthClientEntity extends BaseEntity {

    private static final long serialVersionUID = -8914139658642484148L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 客户端ID
     */
    @TableField("client_id")
    private String clientId;

    /**
     * 客户端名称
     */
    @TableField("client_name")
    private String clientName;

    /**
     * 可访问资源ID
     */
    @TableField("resource_ids")
    private String resourceIds;

    /**
     * 客户端密钥
     */
    @TableField("client_secret")
    private String clientSecret;

    /**
     * 使用范围
     */
    @TableField("scope")
    private String scope;

    /**
     * token获取方式
     */
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;

    /**
     * 授权码模式跳转uri
     */
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;

    /**
     * 权限
     */
    @TableField("authorities")
    private String authorities;

    /**
     * token有效期，单位秒
     */
    @TableField("access_token_validity")
    private Integer accessTokenValidity;

    /**
     * refresh_token有效期，单位秒
     */
    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;

    /**
     * 附加信息
     */
    @TableField("additional_information")
    private String additionalInformation;

    /**
     * 授权码模式自动跳过页面授权步骤
     */
    @TableField("auto_approve")
    private String autoApprove;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    @TableField("locked")
    private Byte locked;

}

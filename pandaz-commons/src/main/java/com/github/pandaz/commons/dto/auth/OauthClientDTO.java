package com.github.pandaz.commons.dto.auth;

import com.github.pandaz.commons.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 客户端
 *
 * @author Carzer
 * @since 2020-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OauthClientDTO", description = "客户端")
public class OauthClientDTO extends BaseDTO {

    private static final long serialVersionUID = -7367804678272243234L;

    /**
     * 客户端ID
     */
    @NotEmpty
    @ApiModelProperty("客户端ID")
    private String clientId;

    /**
     * 客户端名称
     */
    @NotEmpty
    @ApiModelProperty("客户端名称")
    private String clientName;

    /**
     * 可访问资源ID
     */
    @ApiModelProperty("可访问资源ID")
    private String resourceIds;

    /**
     * 客户端密钥
     */
    @ApiModelProperty("客户端密钥")
    private String clientSecret;

    /**
     * 使用范围
     */
    @ApiModelProperty("使用范围")
    private String scope;

    /**
     * token获取方式
     */
    @ApiModelProperty("token获取方式")
    private String authorizedGrantTypes;

    /**
     * 授权码模式跳转uri
     */
    @ApiModelProperty("授权码模式跳转uri")
    private String webServerRedirectUri;

    /**
     * 权限
     */
    @ApiModelProperty("权限")
    private String authorities;

    /**
     * token有效期
     */
    @ApiModelProperty("token有效期")
    private Integer accessTokenValidity;

    /**
     * refresh_token有效期
     */
    @ApiModelProperty("refresh_token有效期")
    private Integer refreshTokenValidity;

    /**
     * 附加信息
     */
    @ApiModelProperty("附加信息")
    private String additionalInformation;

    /**
     * 授权码模式自动跳过页面授权步骤
     */
    @ApiModelProperty("授权码模式自动跳过页面授权步骤")
    private String autoApprove;

    /**
     * 是否锁定
     */
    @ApiModelProperty("是否锁定")
    private Byte locked;
}

package com.pandaz.commons.dto.usercenter;

import com.pandaz.commons.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 权限信息DTO
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionDTO extends BaseDTO {

    private static final long serialVersionUID = -6253234950648940203L;

    /**
     * 主键
     */
    private String id;

    /**
     * 权限名
     */
    private String name;

    /**
     * 权限编码
     */
    @NotEmpty
    private String code;

    /**
     * 系统编码
     */
    private String osCode;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 资源URL
     */
    private String url;

    /**
     * 请求类型get\post\put\delete
     */
    private Byte requestType;

    /**
     * 优先级及位移数
     */
    private Byte priority;

    /**
     * 位运算结果
     */
    private Integer bitResult;

    /**
     * 权限级别，只有组织级别小于等于权限级别，才可使用该权限
     */
    private Integer level;
}

package com.github.pandaz.commons.dto.auth;

import com.github.pandaz.commons.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 组织信息
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OrganizationDTO", description = "组织信息")
public class OrganizationDTO extends BaseDTO {

    private static final long serialVersionUID = -5865998917996643986L;

    /**
     * 组织名
     */
    @ApiModelProperty("组织名")
    @Size(min = 1, max = 100)
    private String name;

    /**
     * 组织编码
     */
    @ApiModelProperty("组织编码")
    @NotEmpty
    @Size(min = 1, max = 50)
    private String code;

    /**
     * 父组织编码
     */
    @ApiModelProperty("父组织编码")
    @Size(min = 1, max = 50)
    private String parentCode;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    @Size(max = 200)
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sorting;

    /**
     * 级别
     */
    @ApiModelProperty("级别")
    private Integer level;

    /**
     * 是否叶子节点
     */
    @ApiModelProperty("是否叶子节点")
    @Min(0)
    @Max(1)
    private Byte isLeafNode;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    @ApiModelProperty("是否锁定(0:未锁定，1:已锁定)")
    @Min(0)
    @Max(1)
    private Byte locked;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @Size(max = 256)
    private String remark;

    /**
     * 子菜单
     */
    @ApiModelProperty("子菜单")
    private List<OrganizationDTO> children;
}

package com.github.pandaz.commons.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础DTO
 *
 * @author Carzer
 * @since 2019-07-17
 */
@Data
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = -4836774517587340612L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String id;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createdDate;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updatedDate;

    /**
     * 版本号
     */
    @ApiModelProperty("版本号")
    private Integer version;

    @ApiModelProperty("租户ID")
    private Long tenantId;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;

    /**
     * 起始页码
     */
    @ApiModelProperty("起始页码")
    private Integer pageNum;

    /**
     * 每页条数
     */
    @ApiModelProperty("每页条数")
    private Integer pageSize;

    /**
     * 获取当前页码，默认为1
     *
     * @return pageNum
     */
    public Integer getPageNum() {
        if (pageNum == null) {
            pageNum = 1;
        }
        return pageNum;
    }

    /**
     * 获取每页大小，默认为10
     *
     * @return pageSize
     */
    public Integer getPageSize() {
        if (pageSize == null) {
            pageSize = 10;
        }
        return pageSize;
    }
}

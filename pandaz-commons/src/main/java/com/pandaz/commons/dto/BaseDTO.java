package com.pandaz.commons.dto;

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
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdDate;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedDate;

    /**
     * 删除人
     */
    private String deletedBy;

    /**
     * 删除时间
     */
    private LocalDateTime deletedDate;

    /**
     * 删除标记
     */
    private String deletedFlag;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;

    /**
     * 起始页码
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
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

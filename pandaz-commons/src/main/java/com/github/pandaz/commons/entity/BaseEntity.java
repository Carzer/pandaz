package com.github.pandaz.commons.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础类
 *
 * @author Carzer
 * @since 2019-08-22
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -231856618304146934L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    @TableField(value = "created_date")
    private LocalDateTime createdDate;
    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField(value = "updated_date", fill = FieldFill.UPDATE, update = "NOW()")
    private LocalDateTime updatedDate;

    /**
     * 删除人
     */
    private String deletedBy;

    /**
     * 删除时间
     */
    @TableField(value = "deleted_date")
    private LocalDateTime deletedDate;

    /**
     * 删除标记(0:未删除，其他:已删除)
     */
    @TableField("deleted_flag")
    @TableLogic
    private String deletedFlag;

    /**
     * 版本号
     */
    @TableField(value = "version", fill = FieldFill.UPDATE, update = "%s+1")
    @Version
    private Integer version;

    /**
     * 开始时间
     */
    @TableField(exist = false)
    private LocalDateTime startDate;

    /**
     * 结束时间
     */
    @TableField(exist = false)
    private LocalDateTime endDate;

    /**
     * 起始页码
     */
    @TableField(exist = false)
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    @TableField(exist = false)
    private Integer pageSize = 10;

    /**
     * 设置pageNum
     *
     * @param pageNum 第几页
     */
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum == null ? 1 : pageNum;
    }

    /**
     * 设置pageSize
     *
     * @param pageSize 一页有多少条
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize == null ? 10 : pageSize;
    }

}

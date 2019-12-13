package com.pandaz.commons.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * pandaz:com.pandaz.usercenter.entity
 * <p>
 * 基础类
 *
 * @author Carzer
 * @date 2019-08-22 14:05
 */
@Data
public class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = -231856618304146934L;
    /**
     * 主键
     */
    private String id;

    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    private Date updatedDate;
    /**
     * 删除人
     */
    private String deletedBy;
    /**
     * 删除时间
     */
    private Date deletedDate;
    /**
     * 删除标记
     */
    private Byte deletedFlag;
    /**
     * 版本号
     */
    private Integer version;

    /**
     * 起始页码
     */
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
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

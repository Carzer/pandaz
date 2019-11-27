package com.pandaz.commons.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * pandaz:com.pandaz.usercenter.dto
 * <p>
 * 基础数据传输类
 *
 * @author Carzer
 * Date: 2019-07-17 13:12
 */
@Data
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = -4836774517587340612L;

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
     * 版本
     */
    private Integer version;

    /**
     * 起始页码
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;
}

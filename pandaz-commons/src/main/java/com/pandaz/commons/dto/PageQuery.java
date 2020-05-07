package com.pandaz.commons.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分页查询
 *
 * @author Carzer
 * @since 2020-04-30
 */
@Data
public class PageQuery<T extends BaseDTO> implements Serializable {

    private static final long serialVersionUID = -2350052629634078067L;

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

    /**
     * 查询数据
     */
    private T data;
}

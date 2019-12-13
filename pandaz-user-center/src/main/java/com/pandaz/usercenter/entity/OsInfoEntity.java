package com.pandaz.usercenter.entity;

import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * pandaz:com.pandaz.usercenter.entity
 * <p>
 * 系统信息
 *
 * @author Carzer
 * @date 2019-10-25 15:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OsInfoEntity extends BaseEntity<OsInfoEntity> {

    private static final long serialVersionUID = 1210965567272983588L;

    /**
     * 系统名
     */
    private String name;

    /**
     * 系统编码
     */
    private String code;

    /**
     * 父编码
     */
    private String parentCode;

    /**
     * 是否锁定
     */
    private Integer locked;
}
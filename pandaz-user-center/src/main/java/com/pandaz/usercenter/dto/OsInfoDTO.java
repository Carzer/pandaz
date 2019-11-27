package com.pandaz.usercenter.dto;

import com.pandaz.commons.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * pandaz:com.pandaz.usercenter.entity
 * <p>
 * 系统信息
 *
 * @author Carzer
 * Date: 2019-10-25 15:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OsInfoDTO extends BaseDTO {

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
}
package com.pandaz.commons.dto.usercenter;

import com.pandaz.commons.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 组-角色
 *
 * @author Carzer
 * @since 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupRoleDTO extends BaseDTO {

    private static final long serialVersionUID = 384141665728196551L;

    /**
     * 组编码
     */
    private String groupCode;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 组编码
     */
    private List<String> groupCodes;

    /**
     * 角色编码
     */
    private List<String> roleCodes;
}

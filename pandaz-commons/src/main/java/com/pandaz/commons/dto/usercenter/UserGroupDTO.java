package com.pandaz.commons.dto.usercenter;

import com.pandaz.commons.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户-组
 *
 * @author Carzer
 * @since 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserGroupDTO extends BaseDTO {

    private static final long serialVersionUID = 2788925334125259927L;
    
    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 组编码
     */
    private String groupCode;

    /**
     * 用户编码列表
     */
    private List<String> userCodes;

    /**
     * 组编码列表
     */
    private List<String> groupCodes;
}

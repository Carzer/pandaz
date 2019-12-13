package com.pandaz.usercenter.mapper;

import com.pandaz.commons.BaseMapper;
import com.pandaz.usercenter.entity.MenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * pandaz:com.pandaz.usercenter.mapper
 * <p>
 * 菜单mapper
 *
 * @author Carzer
 * @date 2019-11-01 10:52
 */
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<MenuEntity> {

    /**
     * 插入方法
     *
     * @param menu menu
     * @return int
     * @author Carzer
     * @date 2019/11/1 15:03
     */
    int insert(MenuEntity menu);

    /**
     * 插入方法
     *
     * @param menu menu
     * @return int
     * @author Carzer
     * @date 2019/11/1 15:03
     */
    int insertSelective(MenuEntity menu);

}
package com.github.pandaz.auth.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pandaz.auth.entity.PermissionEntity;
import com.github.pandaz.commons.mapper.BasisMapper;

import java.util.List;

/**
 * 权限mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface PermissionMapper extends BasisMapper<PermissionEntity> {

    /**
     * 分页方法，系统名称及菜单名称
     *
     * @param page   page
     * @param entity 分页信息
     * @return 执行结果
     */
    IPage<PermissionEntity> getPageWithFullInfo(Page<?> page, PermissionEntity entity);

    /**
     * 查询所有的位移数
     *
     * @param entity 查询信息
     * @return 所有位移数
     */
    List<Byte> selectBitDigits(PermissionEntity entity);
}
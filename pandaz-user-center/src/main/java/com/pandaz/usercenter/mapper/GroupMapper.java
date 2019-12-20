package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Value;

/**
 * 组mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface GroupMapper extends BaseMapper<GroupEntity> {

    /**
     * 根据主键更新
     *
     * @param group group
     * @return int
     */
    int updateByPrimaryKeySelective(GroupEntity group);

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return int
     */
    int deleteByPrimaryKey(@Value("id") String id);

    /**
     * 根据编码删除
     *
     * @param code 编码
     * @return int
     */
    int deleteByCode(@Value("code") String code);

    /**
     * 插入方法
     *
     * @param group group
     * @return 插入结果
     */
    int insertSelective(GroupEntity group);

}
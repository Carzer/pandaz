package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.UserGroupEntity;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 用户-组关系mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface UserGroupMapper extends BaseMapper<UserGroupEntity> {

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return int
     */
    int deleteByPrimaryKey(String id);

    /**
     * 根据用户编码删除
     *
     * @param userCode userCode
     * @return int
     */
    int deleteByUserCode(@Value("userCode") String userCode);

    /**
     * 根据组编码删除
     *
     * @param groupCode groupCode
     * @return int
     */
    int deleteByGroupCode(@Value("groupCode") String groupCode);

    /**
     * 根据用户信息查询
     *
     * @param userGroup userGroup
     * @return java.util.List<com.pandaz.usercenter.entity.UserGroupEntity>
     */
    List<UserGroupEntity> findByUserCode(UserGroupEntity userGroup);

    /**
     * 插入方法
     *
     * @param userGroup userGroup
     * @return 插入结果
     */
    int insertSelective(UserGroupEntity userGroup);
}
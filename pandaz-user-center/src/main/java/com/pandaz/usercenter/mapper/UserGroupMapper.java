package com.pandaz.usercenter.mapper;

import com.pandaz.commons.BaseMapper;
import com.pandaz.usercenter.entity.UserGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * pandaz:com.pandaz.usercenter.mapper
 * <p>
 * 用户-组关系mapper
 *
 * @author Carzer
 * @date 2019-10-23 10:52
 */
@Mapper
@Repository
public interface UserGroupMapper extends BaseMapper<UserGroupEntity> {

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return int
     * @author Carzer
     * @date 2019-08-22 13:19
     */
    int deleteByPrimaryKey(String id);

    /**
     * 根据用户编码删除
     *
     * @param userCode userCode
     * @return int
     * @author Carzer
     * @date 2019/10/25 15:44
     */
    int deleteByUserCode(@Value("userCode") String userCode);

    /**
     * 根据组编码删除
     *
     * @param groupCode groupCode
     * @return int
     * @author Carzer
     * @date 2019/10/25 15:44
     */
    int deleteByGroupCode(@Value("groupCode") String groupCode);

    /**
     * 根据用户信息查询
     *
     * @param userGroup userGroup
     * @return java.util.List<com.pandaz.usercenter.entity.UserGroupEntity>
     * @author Carzer
     * @date 2019/10/25 16:09
     */
    List<UserGroupEntity> findByUserCode(UserGroupEntity userGroup);
}
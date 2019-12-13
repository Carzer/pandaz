package com.pandaz.usercenter.mapper;

import com.pandaz.commons.BaseMapper;
import com.pandaz.usercenter.entity.GroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * pandaz:com.pandaz.usercenter.mapper
 * <p>
 * 组mapper
 *
 * @author Carzer
 * @date 2019-10-23 10:52
 */
@Mapper
@Repository
public interface GroupMapper extends BaseMapper<GroupEntity> {

    /**
     * 根据主键更新
     *
     * @param group group
     * @return int
     * @author Carzer
     * @date 2019/10/25 14:52
     */
    int updateByPrimaryKeySelective(GroupEntity group);

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return int
     * @author Carzer
     * @date 2019/10/25 15:26
     */
    int deleteByPrimaryKey(@Value("id") String id);

    /**
     * 根据编码删除
     *
     * @param code 编码
     * @return int
     * @author Carzer
     * @date 2019/10/25 15:27
     */
    int deleteByCode(@Value("code") String code);

}
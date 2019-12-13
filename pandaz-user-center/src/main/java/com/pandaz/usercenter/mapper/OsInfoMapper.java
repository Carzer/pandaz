package com.pandaz.usercenter.mapper;

import com.pandaz.commons.BaseMapper;
import com.pandaz.usercenter.entity.OsInfoEntity;
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
public interface OsInfoMapper extends BaseMapper<OsInfoEntity> {

}
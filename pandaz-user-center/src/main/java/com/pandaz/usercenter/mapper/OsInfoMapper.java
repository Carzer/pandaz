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
 * Date: 2019-11-01 10:52
 */
@Mapper
@Repository
public interface OsInfoMapper extends BaseMapper<OsInfoEntity> {

    /**
     * 插入方法
     *
     * @param osInfo osInfo
     * @return int
     * @author Carzer
     * Date: 2019/11/1 15:03
     */
    int insert(OsInfoEntity osInfo);

    /**
     * 插入方法
     *
     * @param osInfo osInfo
     * @return int
     * @author Carzer
     * Date: 2019/11/1 15:03
     */
    int insertSelective(OsInfoEntity osInfo);

}
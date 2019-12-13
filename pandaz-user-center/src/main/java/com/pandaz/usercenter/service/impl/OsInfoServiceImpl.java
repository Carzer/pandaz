package com.pandaz.usercenter.service.impl;

import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.OsInfoEntity;
import com.pandaz.usercenter.mapper.OsInfoMapper;
import com.pandaz.usercenter.service.OsInfoService;
import com.pandaz.usercenter.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * pandaz:com.pandaz.usercenter.service.impl
 * <p>
 * 系统信息服务
 *
 * @author Carzer
 * @date 2019-11-01 15:06
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OsInfoServiceImpl implements OsInfoService {

    /**
     * 系统信息mapper
     */
    private final OsInfoMapper osInfoMapper;

    /**
     * 编码检查工具
     */
    private final CheckUtils<OsInfoEntity, OsInfoMapper> checkUtils;

    /**
     * 插入方法
     *
     * @param osInfo osInfo
     * @return com.pandaz.usercenter.entity.OsInfoEntity
     * @author Carzer
     * @date 2019/11/1 15:10
     */
    @Override
    public OsInfoEntity insert(OsInfoEntity osInfo) {
        checkUtils.checkOrSetCode(osInfo, osInfoMapper, "系统编码已存在", null, null);
        osInfo.setId(UuidUtil.getUnsignedUuid());
        osInfoMapper.insert(osInfo);
        return osInfo;
    }
}

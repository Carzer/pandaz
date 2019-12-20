package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.OsInfoEntity;
import com.pandaz.usercenter.mapper.OsInfoMapper;
import com.pandaz.usercenter.service.OsInfoService;
import com.pandaz.usercenter.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统信息服务
 *
 * @author Carzer
 * @since 2019-11-01
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OsInfoServiceImpl extends ServiceImpl<OsInfoMapper, OsInfoEntity> implements OsInfoService {

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
     */
    @Override
    public OsInfoEntity insert(OsInfoEntity osInfo) {
        checkUtils.checkOrSetCode(osInfo, osInfoMapper, "系统编码已存在", null, null);
        osInfo.setId(UuidUtil.getUnsignedUuid());
        osInfoMapper.insert(osInfo);
        return osInfo;
    }
}

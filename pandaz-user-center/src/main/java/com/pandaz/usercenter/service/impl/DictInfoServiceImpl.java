package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.usercenter.entity.DictInfoEntity;
import com.pandaz.usercenter.mapper.DictInfoMapper;
import com.pandaz.usercenter.service.DictInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典信息表 服务实现类
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DictInfoServiceImpl extends ServiceImpl<DictInfoMapper, DictInfoEntity> implements DictInfoService {

    /**
     * 字典信息mapper
     */
    private final DictInfoMapper dictInfoMapper;
}

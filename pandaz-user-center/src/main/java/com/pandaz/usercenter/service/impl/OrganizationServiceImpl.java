package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.usercenter.entity.OrganizationEntity;
import com.pandaz.usercenter.mapper.OrganizationMapper;
import com.pandaz.usercenter.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 组织信息服务
 *
 * @author Carzer
 * @since 2019-12-13
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, OrganizationEntity> implements OrganizationService {

    /**
     * 组织信息mapper
     */
    private final OrganizationMapper organizationMapper;
}

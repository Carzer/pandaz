package com.pandaz.usercenter.service.impl;

import com.pandaz.usercenter.mapper.OrganizationMapper;
import com.pandaz.usercenter.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: 组织信息服务
 *
 * @author carzer
 * @date 2019/12/13
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationServiceImpl implements OrganizationService {

    /**
     * 组织信息mapper
     */
    private final OrganizationMapper organizationMapper;
}

package com.pandaz.usercenter.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.CustomPasswordEncoder;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.GroupEntity;
import com.pandaz.usercenter.entity.UserEntity;
import com.pandaz.usercenter.entity.UserGroupEntity;
import com.pandaz.usercenter.mapper.UserMapper;
import com.pandaz.usercenter.service.GroupService;
import com.pandaz.usercenter.service.UserGroupService;
import com.pandaz.usercenter.service.UserService;
import com.pandaz.usercenter.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息相关服务
 *
 * @author Carzer
 * @since 2019-07-16
 */
@CacheConfig(cacheNames = {"user-center:user"})
@Service("userService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    /**
     * 组服务
     */
    private final GroupService groupService;

    /**
     * 用户-组服务
     */
    private final UserGroupService userGroupService;

    /**
     * 用户mapper
     */
    private final UserMapper userMapper;

    /**
     * 编码检查工具
     */
    private final CheckUtils<UserEntity, UserMapper> checkUtils;

    /**
     * 根据用户名获取用户信息
     *
     * @param loginName 用户名
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    @Cacheable(key = "'loginName:'+#loginName")
    @Override
    public UserEntity loadUserByUsername(String loginName) {
        return userMapper.findByLoginName(loginName);
    }

    /**
     * 根据编码查询
     *
     * @param code code
     * @return com.pandaz.usercenter.entity.UserEntity
     */
    @Cacheable(key = "#code")
    @Override
    public UserEntity findByCode(String code) {
        return userMapper.findByCode(code);
    }

    /**
     * 根据用户编码更新用户信息
     *
     * @param user user
     * @return int
     */
    @CacheEvict(key = "#user.code")
    @Override
    public UserEntity updateByCode(UserEntity user) {
        userMapper.updateByCode(user);
        return userMapper.findByCode(user.getCode());
    }

    /**
     * 插入用户信息
     *
     * @param user 用户
     * @return int
     */
    @Cacheable(key = "#user.code")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserEntity insert(@NotNull UserEntity user) {
        String userCode = checkUtils.checkOrSetCode(user, userMapper, "用户编码已存在", null, null);
        //用户信息补充
        String rawPassword = user.getPassword();
        Assert.hasText(rawPassword, SysConstants.PD_NOT_NULL_WARN);
        String encodedPassword = new CustomPasswordEncoder().encode(rawPassword);
        user.setPassword(encodedPassword);
        String userName = user.getName();
        String createdBy = user.getCreatedBy();
        LocalDateTime createdDate = user.getCreatedDate();
        user.setId(UuidUtil.getUnsignedUuid());
        //建立用户私有组
        String groupCode = SysConstants.GROUP_PREFIX + userCode;
        GroupEntity group = new GroupEntity();
        group.setName(userName + SysConstants.PRIVATE_GROUP);
        group.setCode(groupCode);
        group.setIsPrivate(SysConstants.IS_PRIVATE);
        group.setCreatedBy(createdBy);
        group.setCreatedDate(createdDate);
        //关联用户及私有组
        UserGroupEntity userGroup = new UserGroupEntity();
        userGroup.setId(UuidUtil.getUnsignedUuid());
        userGroup.setUserCode(userCode);
        userGroup.setGroupCode(groupCode);
        userGroup.setCreatedBy(createdBy);
        userGroup.setCreatedDate(createdDate);
        userGroup.setIsPrivate(SysConstants.IS_PRIVATE);
        //插入相关信息
        userGroupService.insert(userGroup);
        groupService.insert(group);
        userMapper.insertSelective(user);
        return user;
    }

    /**
     * 删除用户信息
     *
     * @param userCode userCode
     * @return int
     */
    @CacheEvict(key = "#userCode")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCode(String userCode) {
        //查询私有组，并进行删除
        UserGroupEntity userGroup = new UserGroupEntity();
        userGroup.setIsPrivate(SysConstants.IS_PRIVATE);
        userGroup.setUserCode(userCode);
        List<UserGroupEntity> groupList = userGroupService.findByUserCode(userGroup);
        if (!CollectionUtils.isEmpty(groupList)) {
            groupList.forEach(group -> groupService.deleteByCode(group.getGroupCode()));
        }
        //删除所有用户相关的组关联信息
        userGroupService.deleteByUserCode(userCode);
        //最终删除用户
        return userMapper.deleteByCode(userCode);
    }

    /**
     * 获取用户信息页
     *
     * @param userEntity 查询条件
     * @return java.util.List<com.pandaz.usercenter.entity.UserEntity>
     */
    @Override
    @SentinelResource("user-getPage")
    public IPage<UserEntity> getPage(UserEntity userEntity) {
        Page<UserEntity> page = new Page<>(userEntity.getPageNum(), userEntity.getPageSize());
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        String name = userEntity.getName();
        String code = userEntity.getCode();
        if (StringUtils.hasText(name)) {
            queryWrapper.like("name", name);
        }
        if (StringUtils.hasText(code)) {
            queryWrapper.like("code", code);
        }
        return page(page, queryWrapper);
    }
}

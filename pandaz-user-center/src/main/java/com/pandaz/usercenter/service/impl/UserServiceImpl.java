package com.pandaz.usercenter.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.CustomPasswordEncoder;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.config.SecurityConfig;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", loginName);
        return userMapper.selectOne(queryWrapper);
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
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 根据用户编码更新用户信息
     *
     * @param user user
     * @return int
     */
    @CacheEvict(key = "#user.code")
    @Override
    public int updateByCode(UserEntity user) {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", user.getCode());
        return userMapper.update(user, updateWrapper);
    }

    /**
     * 插入用户信息
     * <p>
     * 由于PasswordEncoder与UserService同在SecurityConfig中实例化
     * {@link SecurityConfig#userDetailsService,SecurityConfig#userService,SecurityConfig#passwordEncoder}
     * 所以Spring无法在初始化时注入PasswordEncoder
     * 故使用新建对象的方式来使用{@link CustomPasswordEncoder}
     *
     * @param user 用户
     * @return 用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserEntity insert(UserEntity user) {
        // 校验重复
        String userCode = checkUtils.checkOrSetCode(user, userMapper, "用户编码已存在", null, null);
        UserEntity loginUser = loadUserByUsername(user.getLoginName());
        if (loginUser != null) {
            throw new IllegalArgumentException("登录名已存在");
        }

        // 用户信息补充
        if (!StringUtils.hasText(user.getId())) {
            user.setId(UuidUtil.getId());
        }
        String rawPass = user.getPassword();
        String encodedPass = SysConstants.DEFAULT_ENCODED_PASS;
        if (StringUtils.hasText(rawPass)) {
            CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();
            encodedPass = passwordEncoder.encode(rawPass);
        }
        user.setPassword(encodedPass);
        String userName = user.getName();
        String createdBy = user.getCreatedBy();
        LocalDateTime createdDate = user.getCreatedDate();

        // 建立用户私有组
        String groupCode = String.format("%s%s", SysConstants.GROUP_PREFIX, userCode);
        GroupEntity group = new GroupEntity();
        group.setName(String.format("%s%s", userName, SysConstants.PRIVATE_GROUP));
        group.setCode(groupCode);
        group.setIsPrivate(SysConstants.PRIVATE);
        group.setCreatedBy(createdBy);
        group.setCreatedDate(createdDate);
        // 关联用户及私有组
        UserGroupEntity userGroup = new UserGroupEntity();
        userGroup.setId(UuidUtil.getId());
        userGroup.setUserCode(userCode);
        userGroup.setGroupCode(groupCode);
        userGroup.setCreatedBy(createdBy);
        userGroup.setCreatedDate(createdDate);
        userGroup.setIsPrivate(SysConstants.PRIVATE);
        // 插入相关信息
        userGroupService.insert(userGroup);
        groupService.insert(group);
        userMapper.insertSelective(user);
        return user;
    }

    /**
     * 删除用户信息
     *
     * @param userEntity 用户信息
     * @return int
     */
    @CacheEvict(key = "#userEntity.code")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCode(UserEntity userEntity) {
        // 查询私有组，并进行删除
        UserGroupEntity userGroup = new UserGroupEntity();
        userGroup.setIsPrivate(SysConstants.PRIVATE);
        userGroup.setUserCode(userEntity.getCode());
        List<UserGroupEntity> groupList = userGroupService.findByUserCode(userGroup);
        if (!CollectionUtils.isEmpty(groupList)) {
            String deletedBy = userEntity.getDeletedBy();
            LocalDateTime deletedDate = userEntity.getDeletedDate();
            groupList.forEach(entity -> {
                GroupEntity groupEntity = new GroupEntity();
                groupEntity.setCode(entity.getGroupCode());
                groupEntity.setDeletedBy(deletedBy);
                groupEntity.setDeletedDate(deletedDate);
                groupService.deleteByCode(groupEntity);
            });
        }
        // 删除所有用户相关的组关联信息
        userGroupService.deleteByUserCode(userEntity);
        // 最终删除用户
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", userEntity.getCode());
        return userMapper.delete(updateWrapper);
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
        if (StringUtils.hasText(userEntity.getName())) {
            queryWrapper.likeRight("name", userEntity.getName());
        }
        if (StringUtils.hasText(userEntity.getCode())) {
            queryWrapper.likeRight("code", userEntity.getCode());
        }
        return page(page, queryWrapper);
    }
}

package com.github.pandaz.auth.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.auth.config.SecurityConfig;
import com.github.pandaz.auth.custom.constants.ExpireStateEnum;
import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.auth.entity.GroupEntity;
import com.github.pandaz.auth.entity.UserEntity;
import com.github.pandaz.auth.entity.UserGroupEntity;
import com.github.pandaz.auth.mapper.UserMapper;
import com.github.pandaz.auth.service.GroupService;
import com.github.pandaz.auth.service.UserGroupService;
import com.github.pandaz.auth.service.UserOrgService;
import com.github.pandaz.auth.service.UserService;
import com.github.pandaz.auth.util.CheckUtil;
import com.github.pandaz.commons.util.CustomPasswordEncoder;
import com.github.pandaz.commons.util.UuidUtil;
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
@CacheConfig(cacheNames = {"auth:user"})
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
     * 用户-组织服务
     */
    private final UserOrgService userOrgService;

    /**
     * 用户mapper
     */
    private final UserMapper userMapper;

    /**
     * 编码检查工具
     */
    private final CheckUtil<UserEntity, UserMapper> checkUtil;

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
     * @param userEntity code
     * @return 用户信息
     */
    @Cacheable(key = "#userEntity.code")
    @Override
    public UserEntity findByCode(UserEntity userEntity) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", userEntity.getCode());
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 根据用户编码更新用户信息
     *
     * @param userEntity user
     * @return int
     */
    @CacheEvict(key = "#userEntity.code")
    @Override
    public int updateByCode(UserEntity userEntity) {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", userEntity.getCode());
        return userMapper.update(userEntity, updateWrapper);
    }

    /**
     * 插入用户信息
     * <p>
     * 由于PasswordEncoder与UserService同在SecurityConfig中实例化
     * {@link SecurityConfig#userDetailsService,SecurityConfig#userService,SecurityConfig#passwordEncoder}
     * 所以Spring无法在初始化时注入PasswordEncoder
     * 故使用新建对象的方式来使用{@link CustomPasswordEncoder}
     *
     * @param userEntity 用户
     * @return 用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(UserEntity userEntity) {

        // 校验重复
        String userCode = checkUtil.checkOrSetCode(userEntity, userMapper, "用户编码重复", null, null);
        UserEntity loginUser = loadUserByUsername(userEntity.getLoginName());
        if (loginUser != null) {
            throw new IllegalArgumentException("登录名已存在");
        }

        // 用户信息补充
        if (!StringUtils.hasText(userEntity.getId())) {
            userEntity.setId(UuidUtil.getId());
        }
        String rawPass = userEntity.getPassword();
        String encodedPass = SysConstants.DEFAULT_ENCODED_PASS;
        if (StringUtils.hasText(rawPass)) {
            CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();
            encodedPass = passwordEncoder.encode(rawPass);
        }
        userEntity.setPassword(encodedPass);
        String userName = userEntity.getName();
        String createdBy = userEntity.getCreatedBy();
        LocalDateTime createdDate = userEntity.getCreatedDate();

        // 建立用户私有组
        GroupEntity group = new GroupEntity();
        group.setName(String.format("%s%s", userName, SysConstants.PRIVATE_GROUP));
        group.setCode(userCode);
        group.setIsPrivate(SysConstants.PRIVATE);
        group.setCreatedBy(createdBy);
        group.setCreatedDate(createdDate);

        // 关联用户及私有组
        UserGroupEntity userGroup = new UserGroupEntity();
        userGroup.setId(UuidUtil.getId());
        userGroup.setUserCode(userCode);
        userGroup.setGroupCode(userCode);
        userGroup.setCreatedBy(createdBy);
        userGroup.setCreatedDate(createdDate);
        userGroup.setIsPrivate(SysConstants.PRIVATE);

        // 插入相关信息
        userGroupService.insert(userGroup);
        groupService.insert(group);
        return userMapper.insertSelective(userEntity);
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
        // 删除所有用户相关的组织关联信息
        userOrgService.deleteByUserCode(userEntity);
        // 最终删除用户
        return userMapper.logicDelete(userEntity);
    }

    /**
     * 获取用户信息页
     *
     * @param userEntity 查询条件
     * @return 执行结果
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
        if (userEntity.getLocked() != null) {
            queryWrapper.eq("locked", userEntity.getLocked());
        }
        if (userEntity.getStartDate() != null) {
            queryWrapper.ge(SysConstants.CREATED_DATE_COLUMN, userEntity.getStartDate());
        }
        if (userEntity.getEndDate() != null) {
            queryWrapper.le(SysConstants.CREATED_DATE_COLUMN, userEntity.getEndDate());
        }
        String expireState = userEntity.getExpireState();
        if (StringUtils.hasText(expireState)) {
            if (ExpireStateEnum.ACTIVE.getVal().equals(expireState)) {
                queryWrapper.ge("expire_at", LocalDateTime.now());
            } else if (ExpireStateEnum.EXPIRED.getVal().equals(expireState)) {
                queryWrapper.lt("expire_at", LocalDateTime.now());
            }
        }
        queryWrapper.orderByDesc(SysConstants.CREATED_DATE_COLUMN);
        return page(page, queryWrapper);
    }

    /**
     * 批量删除用户
     *
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       编码
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return 0;
        }
        codes.forEach(code -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setCode(code);
            userEntity.setDeletedBy(deletedBy);
            userEntity.setDeletedDate(deletedDate);
            deleteByCode(userEntity);
        });
        return codes.size();
    }
}

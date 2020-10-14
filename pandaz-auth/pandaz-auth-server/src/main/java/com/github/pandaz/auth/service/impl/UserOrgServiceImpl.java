package com.github.pandaz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.auth.entity.OrganizationEntity;
import com.github.pandaz.auth.entity.UserEntity;
import com.github.pandaz.auth.entity.UserOrgEntity;
import com.github.pandaz.auth.mapper.UserOrgMapper;
import com.github.pandaz.auth.service.UserOrgService;
import com.github.pandaz.commons.util.ListUtil;
import com.github.pandaz.commons.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户-组织
 *
 * @author Carzer
 * @since 2020-05-21
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserOrgServiceImpl extends ServiceImpl<UserOrgMapper, UserOrgEntity> implements UserOrgService {

    /**
     * 用户-组织mapper
     */
    private final UserOrgMapper userOrgMapper;

    /**
     * 插入方法
     *
     * @param userOrgEntity userOrgEntity
     */
    @Override
    public int insert(UserOrgEntity userOrgEntity) {
        if (!StringUtils.hasText(userOrgEntity.getId())) {
            userOrgEntity.setId(UuidUtil.getId());
        }
        return userOrgMapper.insertSelective(userOrgEntity);
    }

    /**
     * 根据用户编码查询
     *
     * @param userOrgEntity userOrgEntity
     * @return 执行结果
     */
    @Override
    public List<UserOrgEntity> findByUserCode(UserOrgEntity userOrgEntity) {
        QueryWrapper<UserOrgEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserOrgEntity::getUserCode, userOrgEntity.getUserCode());
        return userOrgMapper.selectList(queryWrapper);
    }

    /**
     * 根据用户编码删除
     *
     * @param userEntity userEntity
     * @return int
     */
    @Override
    public int deleteByUserCode(UserEntity userEntity) {
        UserOrgEntity userOrgEntity = new UserOrgEntity();
        userOrgEntity.setUserCode(userEntity.getCode());
        userOrgEntity.setDeletedBy(userEntity.getDeletedBy());
        userOrgEntity.setDeletedDate(userEntity.getDeletedDate());
        return userOrgMapper.logicDeleteByUserCode(userOrgEntity);
    }

    /**
     * 根据组织编码删除
     *
     * @param organizationEntity organizationEntity
     * @return int
     */
    @Override
    public int deleteByOrgCode(OrganizationEntity organizationEntity) {
        UserOrgEntity userOrgEntity = new UserOrgEntity();
        userOrgEntity.setOrgCode(organizationEntity.getCode());
        userOrgEntity.setDeletedBy(organizationEntity.getDeletedBy());
        userOrgEntity.setDeletedDate(organizationEntity.getDeletedDate());
        return userOrgMapper.logicDeleteByOrgCode(userOrgEntity);
    }

    /**
     * 绑定组织成员
     *
     * @param operator      操作者
     * @param currentDate   操作时间
     * @param userOrgEntity 用户信息
     * @return 执行结果
     */
    @Override
    public int bindOrgMember(String operator, LocalDateTime currentDate, UserOrgEntity userOrgEntity) {
        String orgCode = userOrgEntity.getOrgCode();
        userOrgEntity.setUserCode(null);
        List<String> existingCodes = userOrgMapper.listBindOrgMembers(userOrgEntity);
        List<String> newCodes = userOrgEntity.getUserCodes();
        // 已经存在的编码，如果没有在新list中存在，则准备删除
        List<String> codesToRemove = ListUtil.more(existingCodes, newCodes);
        // 新list中增加的编码，则准备保存
        List<String> codesToSave = ListUtil.more(newCodes, existingCodes);
        // 清理之前的关系
        userOrgEntity.setDeletedBy(operator);
        userOrgEntity.setDeletedDate(currentDate);
        deleteByCodes(userOrgEntity, codesToRemove);
        // 保存关系
        List<UserOrgEntity> list = codesToSave.stream().map(code -> {
            UserOrgEntity userOrg = new UserOrgEntity();
            userOrg.setId(UuidUtil.getId());
            userOrg.setOrgCode(orgCode);
            userOrg.setUserCode(code);
            userOrg.setCreatedBy(operator);
            userOrg.setCreatedDate(currentDate);
            return userOrg;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            return userOrgMapper.batchInsert(list);
        }
        return 0;
    }

    /**
     * 绑定用户与组织关系
     *
     * @param operator      操作者
     * @param currentDate   操作时间
     * @param userOrgEntity 用户信息
     * @return 执行结果
     */
    @Override
    public int bindUserOrg(String operator, LocalDateTime currentDate, UserOrgEntity userOrgEntity) {
        String userCode = userOrgEntity.getUserCode();
        userOrgEntity.setOrgCode(null);
        List<String> existingCodes = userOrgMapper.listBindUserOrg(userOrgEntity);
        List<String> newCodes = userOrgEntity.getOrgCodes();
        // 已经存在的编码，如果没有在新list中存在，则准备删除
        List<String> codesToRemove = ListUtil.more(existingCodes, newCodes);
        // 新list中增加的编码，则准备保存
        List<String> codesToSave = ListUtil.more(newCodes, existingCodes);
        // 清理之前的关系
        userOrgEntity.setDeletedBy(operator);
        userOrgEntity.setDeletedDate(currentDate);
        deleteByCodes(userOrgEntity, codesToRemove);
        // 保存关系
        List<UserOrgEntity> list = codesToSave.stream().map(code -> {
            UserOrgEntity userOrg = new UserOrgEntity();
            userOrg.setId(UuidUtil.getId());
            userOrg.setOrgCode(code);
            userOrg.setUserCode(userCode);
            userOrg.setCreatedBy(operator);
            userOrg.setCreatedDate(currentDate);
            return userOrg;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            return userOrgMapper.batchInsert(list);
        }
        return 0;
    }

    /**
     * 列出组织内成员
     *
     * @param userOrgEntity 查询条件
     * @return 执行结果
     */
    @Override
    public List<String> listBindOrgMembers(UserOrgEntity userOrgEntity) {
        return userOrgMapper.listBindOrgMembers(userOrgEntity);
    }

    /**
     * 列出用户所有的组织
     *
     * @param userOrgEntity 查询条件
     * @return 执行结果
     */
    @Override
    public List<String> listBindUserOrg(UserOrgEntity userOrgEntity) {
        return userOrgMapper.listBindUserOrg(userOrgEntity);
    }

    /**
     * 删除绑定关系
     *
     * @param userOrgEntity userOrgEntity
     * @param codes         codes
     */
    private void deleteByCodes(UserOrgEntity userOrgEntity, List<String> codes) {
        if (!CollectionUtils.isEmpty(codes)) {
            Map<String, Object> map = new HashMap<>(5);
            map.put("deletedBy", userOrgEntity.getDeletedBy());
            map.put("deletedDate", userOrgEntity.getDeletedDate());
            map.put("userCode", userOrgEntity.getUserCode());
            map.put("orgCode", userOrgEntity.getOrgCode());
            map.put("list", codes);
            userOrgMapper.logicDeleteByCodes(map);
        }
    }
}

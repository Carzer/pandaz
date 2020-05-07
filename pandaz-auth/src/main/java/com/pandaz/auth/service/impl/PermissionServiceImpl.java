package com.pandaz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.auth.custom.constants.SysConstants;
import com.pandaz.auth.entity.PermissionEntity;
import com.pandaz.auth.mapper.PermissionMapper;
import com.pandaz.auth.service.PermissionService;
import com.pandaz.auth.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限相关服务
 *
 * @author Carzer
 * @since 2019-11-05
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {

    /**
     * 权限mapper
     */
    private final PermissionMapper permissionMapper;

    /**
     * 角色权限服务
     */
    private final RolePermissionService rolePermissionService;

    /**
     * 插入方法
     *
     * @param permissionEntity permission
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(PermissionEntity permissionEntity) {
        QueryWrapper<PermissionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("os_code", permissionEntity.getOsCode());
        queryWrapper.eq("code", permissionEntity.getCode());
        int count = permissionMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new IllegalArgumentException("权限编码重复");
        }
        if (!StringUtils.hasText(permissionEntity.getId())) {
            permissionEntity.setId(UuidUtil.getId());
        }
        setBitResult(permissionEntity);
        return permissionMapper.insertSelective(permissionEntity);
    }

    /**
     * 根据编码查询
     *
     * @param permissionEntity 编码
     * @return 查询结果
     */
    @Override
    public PermissionEntity findByCode(PermissionEntity permissionEntity) {
        QueryWrapper<PermissionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", permissionEntity.getCode());
        return permissionMapper.selectOne(queryWrapper);
    }

    /**
     * 分页方法
     *
     * @param permissionEntity 权限信息
     * @return 分页结果
     */
    @Override
    public IPage<PermissionEntity> getPage(PermissionEntity permissionEntity) {
        Page<PermissionEntity> page = new Page<>(permissionEntity.getPageNum(), permissionEntity.getPageSize());
        return permissionMapper.getPageWithFullInfo(page, permissionEntity);
    }

    /**
     * 更新方法
     *
     * @param permissionEntity 权限信息
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByCode(PermissionEntity permissionEntity) {
        UpdateWrapper<PermissionEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", permissionEntity.getCode());
        setBitResult(permissionEntity);
        return permissionMapper.update(permissionEntity, updateWrapper);
    }

    /**
     * 删除方法
     *
     * @param permissionEntity 权限信息
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCode(PermissionEntity permissionEntity) {
        rolePermissionService.deleteByPermissionCode(permissionEntity);
        return permissionMapper.logicDelete(permissionEntity);
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
            PermissionEntity permissionEntity = new PermissionEntity();
            permissionEntity.setCode(code);
            permissionEntity.setDeletedBy(deletedBy);
            permissionEntity.setDeletedDate(deletedDate);
            deleteByCode(permissionEntity);
        });
        return codes.size();
    }

    /**
     * 根据菜单编码删除
     *
     * @param permissionEntity 编码信息
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByMenuCode(PermissionEntity permissionEntity) {
        QueryWrapper<PermissionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_code", permissionEntity.getMenuCode());
        List<PermissionEntity> list = permissionMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(this::deleteByCode);
            return list.size();
        }
        return 0;
    }

    /**
     * 设置权限值
     *
     * @param permissionEntity 条件
     */
    private void setBitResult(PermissionEntity permissionEntity) {
        List<Byte> list = permissionMapper.selectBitDigits(permissionEntity);
        byte bitDigit = SysConstants.MIN_DIGIT;
        int bitResult = 1 << SysConstants.MIN_DIGIT;
        if (!CollectionUtils.isEmpty(list)) {
            Byte i = 0;
            while (i <= SysConstants.MAX_DIGIT && list.contains(i)) {
                i++;
            }
            if (i > SysConstants.MAX_DIGIT) {
                throw new IllegalArgumentException("超出最大权限数量，请联系管理员");
            } else {
                bitDigit = i;
                bitResult = 1 << i;
            }
        }
        permissionEntity.setBitDigit(bitDigit);
        permissionEntity.setBitResult(bitResult);
    }
}

package com.github.pandaz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.auth.entity.PermissionEntity;
import com.github.pandaz.auth.mapper.PermissionMapper;
import com.github.pandaz.auth.service.PermissionService;
import com.github.pandaz.auth.service.RolePermissionService;
import com.github.pandaz.commons.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;
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
        queryWrapper.lambda().eq(PermissionEntity::getOsCode, permissionEntity.getOsCode());
        queryWrapper.lambda().eq(PermissionEntity::getCode, permissionEntity.getCode());
        int count = permissionMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new IllegalArgumentException("权限编码重复");
        }
        if (!StringUtils.hasText(permissionEntity.getId())) {
            permissionEntity.setId(UuidUtil.getId());
        }
        if (permissionEntity.getBitDigit() != null) {
            permissionEntity.setBitResult(1 << permissionEntity.getBitDigit());
        } else {
            setBitResult(permissionEntity);
        }
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
        queryWrapper.lambda().eq(PermissionEntity::getCode, permissionEntity.getCode());
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
        updateWrapper.lambda().eq(PermissionEntity::getCode, permissionEntity.getCode());
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
    public int logicDeleteByCode(PermissionEntity permissionEntity) {
        rolePermissionService.deleteByPermissionCode(permissionEntity);
        return permissionMapper.logicDeleteByCode(permissionEntity);
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
    public int logicDeleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return 0;
        }
        codes.forEach(code -> {
            PermissionEntity permissionEntity = new PermissionEntity();
            permissionEntity.setCode(code);
            permissionEntity.setDeletedBy(deletedBy);
            permissionEntity.setDeletedDate(deletedDate);
            logicDeleteByCode(permissionEntity);
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
        queryWrapper.lambda().eq(PermissionEntity::getMenuCode, permissionEntity.getMenuCode());
        List<PermissionEntity> list = permissionMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(this::logicDeleteByCode);
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
        if (list == null) {
            list = Collections.emptyList();
        }
        Byte bitDigit = permissionEntity.getBitDigit();
        if (bitDigit != null) {
            if (bitDigit > SysConstants.MAX_DIGIT || bitDigit < SysConstants.MIN_DIGIT) {
                throw new IllegalArgumentException("超出权限范围(0-25)");
            } else if (list.contains(bitDigit)) {
                throw new IllegalArgumentException("位移值重复");
            }
        } else {
            Byte i = SysConstants.MIN_DIGIT;
            while (i <= SysConstants.MAX_DIGIT && list.contains(i)) {
                i++;
            }
            if (i > SysConstants.MAX_DIGIT) {
                throw new IllegalArgumentException("超出最大权限数量，请联系管理员");
            }
            bitDigit = i;
            permissionEntity.setBitDigit(bitDigit);
        }
        permissionEntity.setBitResult(1 << bitDigit);
    }
}

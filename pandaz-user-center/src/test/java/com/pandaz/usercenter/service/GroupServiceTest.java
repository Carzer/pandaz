package com.pandaz.usercenter.service;

import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.GroupEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 组信息测试
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Transactional
public class GroupServiceTest extends BasisUnitTest {

    private GroupService groupService;

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Test
    public void insert() {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setCode("group_test3");
        groupEntity.setName("测试组信息");
        groupService.insert(groupEntity);
    }

    @Test
    public void deleteByCode() {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setCode("group_test");
        groupEntity.setDeletedBy("admin");
        groupEntity.setDeletedDate(LocalDateTime.now());
        groupService.deleteByCode(groupEntity);
    }

    @Test
    public void findByCode() {
        groupService.findByCode("group_test");
    }

    @Test
    public void getPage() {
        groupService.getPage(new GroupEntity());
    }

    @Test
    public void updateByCode() {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setCode("group_test");
        groupEntity.setName("测试组信息");
        groupService.updateByCode(groupEntity);
    }
}
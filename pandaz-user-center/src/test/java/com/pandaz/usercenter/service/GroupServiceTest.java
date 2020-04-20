package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.GroupEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.*;

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
        int result = groupService.insert(groupEntity);
        assertEquals(1, result);
    }

    @Test
    public void deleteByCode() {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setCode("group_test");
        groupEntity.setDeletedBy("admin");
        groupEntity.setDeletedDate(LocalDateTime.now());
        int result = groupService.deleteByCode(groupEntity);
        assertThat(result, anything());
    }

    @Test
    public void findByCode() {
        GroupEntity test = groupService.findByCode("group_test");
        assertThat(test, anything());
    }

    @Test
    public void getPage() {
        IPage<GroupEntity> page = groupService.getPage(new GroupEntity());
        assertNotNull(page);
    }

    @Test
    public void updateByCode() {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setCode("group_test");
        groupEntity.setName("测试组信息");
        int result = groupService.updateByCode(groupEntity);
        assertThat(result, anything());
    }
}
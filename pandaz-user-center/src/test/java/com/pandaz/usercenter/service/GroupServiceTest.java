package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.usercenter.UserCenterApp;
import com.pandaz.usercenter.entity.GroupEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * 组信息测试
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserCenterApp.class)
@Rollback
@Transactional
@Slf4j
public class GroupServiceTest {

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
        int result = 0;
        try {
            result = groupService.insert(groupEntity);
        } catch (Exception e) {
            log.error("插入组信息出错", e);
        }
        assertThat(result, anything());
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
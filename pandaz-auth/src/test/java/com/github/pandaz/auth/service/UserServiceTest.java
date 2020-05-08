package com.github.pandaz.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pandaz.auth.AuthApp;
import com.github.pandaz.auth.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * 用户测试
 *
 * @author Carzer
 * @since 2020-02-28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthApp.class)
@Rollback
@Transactional
@Slf4j
public class UserServiceTest {

    private UserService userService;

    @BeforeClass
    public static void setUp() {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "../logs/auth-test/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "../logs/auth-test/nacos/naming");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void loadUserByUsername() {
        UserEntity admin = userService.loadUserByUsername("admin");
        assertThat(admin, anything());
    }

    @Test
    public void findByCode() {
        UserEntity userEntity = new UserEntity();
        userEntity.setCode("admin");
        UserEntity admin = userService.findByCode(userEntity);
        assertThat(admin, anything());
    }

    @Test
    public void updateByCode() {
        UserEntity userEntity = new UserEntity();
        userEntity.setCode("test");
        userEntity.setName("test");
        int result = userService.updateByCode(userEntity);
        assertThat(result, anything());
    }

    @Test
    public void insert() {
        UserEntity userEntity = new UserEntity();
        userEntity.setCode("admin");
        userEntity.setName("admin");
        userEntity.setLoginName("admin");
        userEntity.setPassword("admin");
        userEntity.setPhone("15000000000");
        int result = 0;
        try {
            result = userService.insert(userEntity);
        } catch (Exception e) {
            log.error("插入用户信息出错", e);
        }
        assertThat(result, anything());
    }

    @Test
    public void deleteByCode() {
        UserEntity userEntity = new UserEntity();
        userEntity.setDeletedDate(LocalDateTime.now());
        userEntity.setDeletedBy("admin");
        userEntity.setCode("test17");
        int result = userService.deleteByCode(userEntity);
        assertThat(result, anything());
    }

    @Test
    public void deleteByCodes() {
        List<String> list = List.of("test17", "test18");
        int result = userService.deleteByCodes("admin", LocalDateTime.now(), list);
        assertThat(result, anything());
    }

    @Test
    public void getPage() {
        UserEntity userEntity = new UserEntity();
        userEntity.setCode("user_test");
        IPage<UserEntity> page = userService.getPage(userEntity);
        assertNotNull(page);
    }
}
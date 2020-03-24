package com.pandaz.usercenter.service;

import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.UserEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户测试
 *
 * @author Carzer
 * @since 2020-02-28
 */
@Transactional
public class UserServiceTest extends BasisUnitTest {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void loadUserByUsername() {
        userService.loadUserByUsername("admin");
    }

    @Test
    public void findByCode() {
        userService.findByCode("admin");
    }

    @Test
    public void updateByCode() {
        UserEntity userEntity = new UserEntity();
        userEntity.setCode("test");
        userEntity.setName("test");
        userService.updateByCode(userEntity);
    }

    @Test
    public void insert() {
        UserEntity userEntity = new UserEntity();
        userEntity.setCode("admin");
        userEntity.setName("admin");
        userEntity.setLoginName("admin");
        userEntity.setPassword("admin");
        userEntity.setPhone("15000000000");
        userService.insert(userEntity);
    }

    @Test
    public void deleteByCode() {
        UserEntity userEntity = new UserEntity();
        userEntity.setDeletedDate(LocalDateTime.now());
        userEntity.setDeletedBy("admin");
        userEntity.setCode("test17");
        userService.deleteByCode(userEntity);
    }

    @Test
    public void deleteByCodes() {
        List<String> list = List.of("test17", "test18");
        userService.deleteByCodes("admin", LocalDateTime.now(), list);
    }

    @Test
    public void getPage() {
        UserEntity userEntity = new UserEntity();
        userEntity.setCode("user_test");
        userService.getPage(userEntity);
    }
}
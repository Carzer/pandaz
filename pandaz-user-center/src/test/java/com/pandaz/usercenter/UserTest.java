package com.pandaz.usercenter;

import com.pandaz.commons.util.CustomPasswordEncoder;
import com.pandaz.usercenter.entity.UserEntity;
import com.pandaz.usercenter.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户相关测试类
 *
 * @author Carzer
 * @since 2019-10-23
 */
public class UserTest extends BasisUnitTest {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    @Transactional
    public void changeUser() {
        UserEntity sysUser = userService.loadUserByUsername("admin");
        sysUser.setPassword(new CustomPasswordEncoder().encode("admin"));
        userService.updateByCode(sysUser);
    }

    @Test
    @Transactional
    public void insert() {
        UserEntity user = new UserEntity();
        user.setCode("test10");
        user.setName("test10");
        user.setPassword("test10");
        user.setLoginName("test10");
        user.setPhone("15192782889");
        user.setCreatedBy("admin");
        user.setCreatedDate(LocalDateTime.now());
        user.setExpireAt(LocalDateTime.now().plusMonths(6));
        userService.insert(user);
    }

    @Test
    @Transactional
    public void delete() {
        UserEntity user = new UserEntity();
        user.setCode("test1");
        userService.deleteByCode(user.getCode());
    }
}

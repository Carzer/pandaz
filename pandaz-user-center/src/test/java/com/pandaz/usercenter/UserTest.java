package com.pandaz.usercenter;

import com.pandaz.commons.util.CustomPasswordEncoder;
import com.pandaz.usercenter.entity.UserEntity;
import com.pandaz.usercenter.mapper.UserMapper;
import com.pandaz.usercenter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * 用户相关测试类
 *
 * @author Carzer
 * @since 2019-10-23
 */
//@Rollback
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserTest extends BasisUnitTest {

    private final UserMapper userMapper;

    private final UserService userService;

    @Test
    public void changeUser() {
        UserEntity sysUser = userMapper.findByLoginName("admin");
        sysUser.setPassword(new CustomPasswordEncoder().encode("admin"));
        userMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Test
    public void insert() {
        UserEntity user = new UserEntity();
        user.setCode("test1");
        user.setName("test1");
        user.setPassword("test1");
        user.setLoginName("test1");
        user.setPhone("15192782889");
        user.setCreatedBy("admin");
        user.setCreatedDate(LocalDateTime.now());
        user.setExpireAt(LocalDateTime.now().plusMonths(6));
        userService.insert(user);
    }

    @Test
//    @Transactional
    public void delete() {
        UserEntity user = new UserEntity();
        user.setCode("test1");
        userService.deleteByCode(user.getCode());
    }
}

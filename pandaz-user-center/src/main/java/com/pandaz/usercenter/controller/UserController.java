package com.pandaz.usercenter.controller;

import com.github.pagehelper.Page;
import com.pandaz.commons.util.DozerConvertUtil;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.usercenter.dto.UserDTO;
import com.pandaz.usercenter.dto.UserPwdDTO;
import com.pandaz.usercenter.entity.UserEntity;
import com.pandaz.usercenter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * pandaz:com.pandaz.usercenter.controller
 * <p>
 * 用户相关controller
 *
 * @author Carzer
 * Date: 2019-07-17 15:20
 */
@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    /**
     * 用户信息服务
     */
    private final UserService userService;

    /**
     * 获取用户分页信息
     *
     * @param userDTO userDTO
     * @return com.pandaz.commons.util.ExecuteResult<java.util.Map<java.lang.String,java.lang.Object>>
     * @author Carzer
     * Date: 2019/10/23 17:18
     */
    @GetMapping("/getPage")
    public ExecuteResult<Map<String, Object>> getPage(UserDTO userDTO) {
        ExecuteResult<Map<String, Object>> result = new ExecuteResult<>();
        try {
            UserEntity userEntity = DozerConvertUtil.convert(userDTO, UserEntity.class);
            Page<UserEntity> page = userService.getPage(userEntity);
            result.setData(DozerConvertUtil.convertToMap(page, UserDTO.class));
        } catch (Exception e) {
            log.error("获取用户信息分页出错了：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 根据用户编码获取用户信息
     *
     * @param userDTO 查询条件
     * @return com.pandaz.commons.util.ExecuteResult<com.pandaz.usercenter.dto.UserDTO>
     * @author Carzer
     * Date: 2019/10/28 16:46
     */
    @GetMapping
    public ExecuteResult<UserDTO> get(@RequestBody UserDTO userDTO) {
        ExecuteResult<UserDTO> result = new ExecuteResult<>();
        try {
            UserDTO dto = DozerConvertUtil.convert(userService.findByCode(userDTO.getCode()), UserDTO.class);
            result.setData(dto);
        } catch (Exception e) {
            log.error("根据编码查找用户信息出错了：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 插入用户信息
     *
     * @param userPwdDTO 用户信息
     * @return com.pandaz.commons.util.ExecuteResult<com.pandaz.usercenter.dto.UserDTO>
     * @author Carzer
     * Date: 2019/10/28 17:32
     */
    @PostMapping
    public ExecuteResult<UserDTO> insert(@RequestBody UserPwdDTO userPwdDTO, Principal principal) {
        ExecuteResult<UserDTO> result = new ExecuteResult<>();
        try {
            UserDTO userDTO = userPwdDTO.getUserDTO();
            String password = userPwdDTO.getPassword();
            Date date = new Date();
            UserEntity user = DozerConvertUtil.convert(userDTO, UserEntity.class);
            user.setPassword(password);
            user.setCreatedBy(principal.getName());
            user.setCreatedDate(date);
            //如果没有选择过期时间，就默认6个月后过期
            Timestamp expireAt = user.getExpireAt();
            if (expireAt == null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.MONTH, 6);
                user.setExpireAt(new Timestamp(cal.getTimeInMillis()));
            }
            userService.insert(user);
            result.setData(DozerConvertUtil.convert(user, UserDTO.class));
        } catch (Exception e) {
            log.error("插入用户信息出错了：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param userDTO userDTO
     * @return com.pandaz.commons.util.ExecuteResult<com.pandaz.usercenter.dto.UserDTO>
     * @author Carzer
     * Date: 2019/10/29 09:05
     */
    @PutMapping
    public ExecuteResult<String> update(@RequestBody UserDTO userDTO) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            UserEntity userEntity = DozerConvertUtil.convert(userDTO, UserEntity.class);
            userService.updateByCode(userEntity);
            result.setData("更新成功。");
        } catch (Exception e) {
            log.error("更新用户出错了：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 删除方法
     *
     * @param userDTO userDTO
     * @return com.pandaz.commons.util.ExecuteResult<com.pandaz.usercenter.dto.UserDTO>
     * @author Carzer
     * Date: 2019/10/29 10:15
     */
    @DeleteMapping
    public ExecuteResult<String> delete(@RequestBody UserDTO userDTO) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            String userCode = userDTO.getCode();
            Assert.notNull(userCode,"用户编码不能为空！");
            userService.deleteByCode(userCode);
            result.setData("删除成功。");
        } catch (Exception e) {
            log.error("删除用户出错了：", e);
            result.setError(e.getMessage());
        }
        return result;
    }
}

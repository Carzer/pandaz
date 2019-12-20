package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.UserDTO;
import com.pandaz.commons.dto.usercenter.UserPwdDTO;
import com.pandaz.commons.util.BeanCopierUtil;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.usercenter.entity.UserEntity;
import com.pandaz.usercenter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户相关controller
 *
 * @author Carzer
 * @since 2019-07-17
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
     * @return com.pandaz.commons.util.ExecuteResult<java.util.Map < java.lang.String, java.lang.Object>>
     */
    @GetMapping("/getPage")
    public ExecuteResult<ConcurrentHashMap<String, Object>> getPage(UserDTO userDTO) {
        ExecuteResult<ConcurrentHashMap<String, Object>> result = new ExecuteResult<>();
        try {
            IPage<UserEntity> page = userService.getPage(BeanCopierUtil.copy(userDTO, UserEntity.class));
            result.setData(BeanCopierUtil.convertToMap(page, UserDTO.class));
        } catch (Exception e) {
            log.error("获取用户信息分页异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 根据用户编码获取用户信息
     *
     * @param userDTO 查询条件
     * @return com.pandaz.commons.util.ExecuteResult<com.pandaz.usercenter.dto.UserDTO>
     */
    @GetMapping
    public ExecuteResult<UserDTO> get(@RequestBody UserDTO userDTO) {
        ExecuteResult<UserDTO> result = new ExecuteResult<>();
        try {
            UserDTO dto = BeanCopierUtil.copy(userService.findByCode(userDTO.getCode()), UserDTO.class);
            result.setData(dto);
        } catch (Exception e) {
            log.error("根据编码查找用户信息异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 插入用户信息
     *
     * @param userPwdDTO 用户信息
     * @return com.pandaz.commons.util.ExecuteResult<com.pandaz.usercenter.dto.UserDTO>
     */
    @PostMapping
    public ExecuteResult<UserDTO> insert(@RequestBody UserPwdDTO userPwdDTO, Principal principal) {
        ExecuteResult<UserDTO> result = new ExecuteResult<>();
        try {
            UserDTO userDTO = userPwdDTO.getUserDTO();
            String password = userPwdDTO.getPassword();
            UserEntity user = BeanCopierUtil.copy(userDTO, UserEntity.class);
            user.setPassword(password);
            user.setCreatedBy(principal.getName());
            user.setCreatedDate(LocalDateTime.now());
            //如果没有选择过期时间，就默认6个月后过期
            LocalDateTime expireAt = user.getExpireAt();
            if (expireAt == null) {
                user.setExpireAt(LocalDateTime.now().plusMonths(6L));
            }
            userService.insert(user);
            result.setData(BeanCopierUtil.copy(user, UserDTO.class));
        } catch (Exception e) {
            log.error("插入用户信息异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param userDTO userDTO
     * @return com.pandaz.commons.util.ExecuteResult<com.pandaz.usercenter.dto.UserDTO>
     */
    @PutMapping
    public ExecuteResult<String> update(@Valid @RequestBody UserDTO userDTO) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            UserEntity userEntity = BeanCopierUtil.copy(userDTO, UserEntity.class);
            userService.updateByCode(userEntity);
            result.setData("更新成功。");
        } catch (Exception e) {
            log.error("更新用户异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 删除方法
     *
     * @param userDTO userDTO
     * @return com.pandaz.commons.util.ExecuteResult<com.pandaz.usercenter.dto.UserDTO>
     */
    @DeleteMapping
    public ExecuteResult<String> delete(@Valid @RequestBody UserDTO userDTO) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            String userCode = userDTO.getCode();
            userService.deleteByCode(userCode);
            result.setData("删除成功。");
        } catch (Exception e) {
            log.error("删除用户异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }
}

package com.pandaz.redis.controller;

import com.pandaz.commons.util.R;
import com.pandaz.redis.service.RedisHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * redis 操作
 *
 * @author Carzer
 * @since 2019-10-28
 */
@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OperationController {

    /**
     * redis操作
     */
    private final RedisHelper redisHelper;

    /**
     * 获取Redis value
     *
     * @param key key
     * @return 执行结果
     */
    @GetMapping("/getValue")
    public R<Object> getValue(String key) {
        return new R<>(redisHelper.getObject(key));
    }

    /**
     * 获取Redis value
     *
     * @param value value
     * @return 执行结果
     */
    @PostMapping("/setValue")
    public R<String> setValue(String value) {
        redisHelper.setObject("test", value);
        return R.success();
    }
}

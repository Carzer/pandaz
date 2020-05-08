package com.github.pandaz.redis.controller;

import com.github.pandaz.commons.util.R;
import com.github.pandaz.redis.service.RedisHelper;
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
    @GetMapping("/getObject")
    public R<Object> getObject(String key) {
        return new R<>(redisHelper.getObject(key));
    }

    /**
     * 设置Redis value
     *
     * @param key   key
     * @param value value
     * @param ttl   存活时间
     * @return 执行结果
     */
    @PostMapping("/setObject")
    public R<String> setObject(String key, String value, long ttl) {
        if (0 == ttl) {
            redisHelper.setObject(key, value);
        } else {
            redisHelper.setObject(key, value, ttl);
        }
        return R.success();
    }
}

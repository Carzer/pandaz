package com.pandaz.redis.controller;

import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.redis.service.RedisHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * slf4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationController.class);

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
    public ExecuteResult<String> getValue(String key) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            Object object = redisHelper.getObject(key);
            if (object != null) {
                result.setData(object.toString());
            } else {
                result.setError("nothing from redis");
            }
        } catch (Exception e) {
            LOGGER.error("获取Redis value异常:", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 获取Redis value
     *
     * @param value value
     * @return 执行结果
     */
    @PostMapping("/setValue")
    public ExecuteResult<String> setValue(String value) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            redisHelper.setObject("test", value);
            result.setData("test");
        } catch (Exception e) {
            LOGGER.error("设置Redis value异常:", e);
            result.setError(e.getMessage());
        }
        return result;
    }
}

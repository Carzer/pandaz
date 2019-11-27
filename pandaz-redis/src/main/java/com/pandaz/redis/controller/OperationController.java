package com.pandaz.redis.controller;

import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.redis.service.RedisHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * pandaz:com.pandaz.redis.controller
 * <p>
 * redis 操作
 *
 * @author Carzer
 * Date: 2019-10-28 10:25
 */
@RestController
@RequestMapping("/redis")
public class OperationController {

    /**
     * slf4j
     */
    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationController.class);

    /**
     * redis操作
     */
    @Autowired
    private RedisHelper<String, String> redisHelper;

    /**
     * 获取Redis value
     *
     * @param key key
     * @return com.pandaz.commons.util.ExecuteResult<java.lang.Object>
     * @author Carzer
     * Date: 2019/10/28 10:29
     */
    @GetMapping("/getValue")
    public ExecuteResult<Object> getValue(String key) {
        ExecuteResult<Object> result = new ExecuteResult<>();
        try {
            Object object = redisHelper.getObject(key);
            result.setData(object);
        } catch (Exception e) {
            LOGGER.error("获取Redis value出错了:", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 获取Redis value
     *
     * @param value value
     * @return com.pandaz.commons.util.ExecuteResult<java.lang.String>
     * @author Carzer
     * Date: 2019/10/28 10:32
     */
    @PostMapping("/setValue")
    public ExecuteResult<String> setValue(String value) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            redisHelper.setObject("test", value);
            result.setData("test");
        } catch (Exception e) {
            LOGGER.error("设置Redis value出错了:", e);
            result.setError(e.getMessage());
        }
        return result;
    }
}

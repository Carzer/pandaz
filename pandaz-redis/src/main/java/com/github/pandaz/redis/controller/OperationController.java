package com.github.pandaz.redis.controller;

import com.github.pandaz.commons.util.R;
import com.github.pandaz.redis.service.RedisHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Operation", tags = "Redis操作")
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "查询方法", notes = "查询方法")
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "value", value = "value", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ttl", value = "超时时间，单位：秒", dataType = "integer", paramType = "query")
    })
    @ApiOperation(value = "保存方法", notes = "将验证码的计算结果放入Redis")
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

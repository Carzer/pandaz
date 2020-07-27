package com.github.pandaz.redis.controller;

import com.github.pandaz.commons.util.R;
import com.github.pandaz.redis.config.redis.TargetRedisTemplate;
import com.github.pandaz.redis.service.RedisHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            @ApiImplicitParam(name = "ttl", value = "超时时间，单位：秒", dataType = "Long", paramType = "query")
    })
    @ApiOperation(value = "保存方法", notes = "保存方法")
    @PostMapping("/setObject")
    public R<String> setObject(String key, String value, @RequestParam(name = "ttl", required = false) Long ttl) {
        if (ttl == null || ttl == 0) {
            redisHelper.setObject(key, value);
        } else {
            redisHelper.setObject(key, value, ttl);
        }
        return R.success();
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
            @ApiImplicitParam(name = "ttl", value = "超时时间，单位：秒", dataType = "Long", paramType = "query")
    })
    @ApiOperation(value = "保存方法", notes = "保存方法")
    @PostMapping("/setObjectToSentinel")
    @TargetRedisTemplate("sentinel")
    public R<String> setObjectToSentinel(String key, String value, @RequestParam(name = "ttl", required = false) Long ttl) {
        return setObject(key, value, ttl);
    }


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
    @GetMapping("/getObjectFromSentinel")
    @TargetRedisTemplate("sentinel")
    public R<Object> getObjectFromSentinel(String key) {
        return new R<>(redisHelper.getObject(key));
    }

}

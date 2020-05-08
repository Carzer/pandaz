package com.github.pandaz.redis.service;

import com.github.pandaz.commons.constants.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis操作类 所有需要存入redis的对象都必须序列化
 *
 * @author Carzer
 * @since 2019-07-02
 */
@Component
@SuppressWarnings("unchecked")
public class RedisHelper {

    /**
     * redis服务
     */
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    }

    /**
     * 构造方法
     *
     * @author Carzer
     */
    private RedisHelper() {
    }

    /**
     * 设置对象
     *
     * @param key   键
     * @param value 值
     * @return 执行结果
     */
    public boolean setObject(String key, Object value) {
        SessionCallback<Object> sessionCallback = new SessionCallback<>() {
            @Override
            public List<Object> execute(RedisOperations redisOperations) {
                redisOperations.multi();
                redisOperations.opsForValue().set(String.format("%s%s", RedisConstants.REDIS_PREFIX, key), value);
                return redisOperations.exec();
            }
        };
        redisTemplate.execute(sessionCallback);
        return true;
    }

    /**
     * 设置对象
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间，单位秒
     * @return 执行结果
     */
    public boolean setObject(String key, Object value, long time) {
        SessionCallback<Object> sessionCallback = new SessionCallback<>() {
            @Override
            public List<Object> execute(RedisOperations redisOperations) {
                redisOperations.multi();
                redisOperations.opsForValue().set(String.format("%s%s", RedisConstants.REDIS_PREFIX, key), value, time, TimeUnit.SECONDS);
                return redisOperations.exec();
            }
        };
        redisTemplate.execute(sessionCallback);
        return true;
    }

    /**
     * 设置对象list
     *
     * @param map map
     * @return 执行结果
     */
    public boolean setObjectList(Map<String, Object> map) {
        if (CollectionUtils.isEmpty(map)) {
            return false;
        } else {
            Map<String, Object> setMap = new HashMap<>(map.size());
            map.forEach((key, value) -> setMap.put(String.format("%s%s", RedisConstants.REDIS_PREFIX, key), value));
            SessionCallback<Object> sessionCallback = new SessionCallback<>() {
                @Override
                public List<Object> execute(RedisOperations redisOperations) {
                    redisOperations.multi();
                    redisOperations.opsForValue().multiSet(setMap);
                    return redisOperations.exec();
                }
            };
            redisTemplate.execute(sessionCallback);
            return true;
        }
    }

    /**
     * 获取对象
     *
     * @param key 键
     * @return 对象
     */
    public Object getObject(String key) {
        SessionCallback<Object> sessionCallback = new SessionCallback<>() {
            @Override
            public Object execute(RedisOperations redisOperations) {
                redisOperations.multi();
                redisOperations.opsForValue().get(String.format("%s%s", RedisConstants.REDIS_PREFIX, key));
                List<Object> v = redisOperations.exec();
                if (CollectionUtils.isEmpty(v)) {
                    return null;
                } else {
                    return v.get(0);
                }
            }
        };
        return redisTemplate.execute(sessionCallback);
    }

    /**
     * 获取对象列表
     *
     * @param pattern 表达式 keys*
     * @return 对象
     */
    public List<Object> getObjectList(String pattern) {
        Set<String> keys = redisTemplate.keys(String.format("%s%s", RedisConstants.REDIS_PREFIX, pattern));
        if (CollectionUtils.isEmpty(keys)) {
            return new ArrayList<>();
        }
        SessionCallback<List<Object>> sessionCallback = new SessionCallback<>() {
            @Override
            public List<Object> execute(RedisOperations redisOperations) {
                redisOperations.multi();
                redisOperations.opsForValue().multiGet(keys);
                List<Object> v = redisOperations.exec();
                List<Object> list = new ArrayList<>();
                if (!CollectionUtils.isEmpty(v)) {
                    list.addAll((List<Object>) v.get(0));
                }
                return list;
            }
        };
        return redisTemplate.execute(sessionCallback);
    }

    /**
     * 删除对象
     *
     * @param key 键
     * @return 执行结果
     */
    public boolean deleteObject(String key) {
        SessionCallback<Object> sessionCallback = new SessionCallback<>() {
            @Override
            public List<Object> execute(RedisOperations operations) {
                operations.multi();
                operations.delete(String.format("%s%s", RedisConstants.REDIS_PREFIX, key));
                return operations.exec();
            }
        };
        redisTemplate.execute(sessionCallback);
        return true;
    }

    /**
     * 根据表达式删除对象
     *
     * @param pattern 表达式 keys*
     * @return 执行结果
     */
    public boolean deleteObjectList(String pattern) {
        Set<String> keys = redisTemplate.keys(String.format("%s%s", RedisConstants.REDIS_PREFIX, pattern));
        if (CollectionUtils.isEmpty(keys)) {
            return false;
        }
        SessionCallback<Object> sessionCallback = new SessionCallback<>() {
            @Override
            public List<Object> execute(RedisOperations operations) {
                operations.multi();
                operations.delete(keys);
                return operations.exec();
            }
        };
        redisTemplate.execute(sessionCallback);
        return true;
    }
}

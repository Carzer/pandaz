package com.pandaz.redis.service;

import com.pandaz.commons.constants.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * redis操作类 所有需要存入redis的对象都必须序列化
 *
 * @param <K> 目前仅支持String类型
 * @author Carzer
 * Date: 2019-07-02
 */
@Component
@SuppressWarnings("unchecked")
public class RedisHelper<K, V> {

    /**
     * redis服务
     */
    private RedisTemplate<K, V> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    }

    /**
     * 构造方法
     *
     * @author Carzer
     * Date: 2019-07-02
     */
    private RedisHelper() {
    }

    /**
     * 设置对象
     *
     * @param key   键
     * @param value 值
     * @return 执行结果
     * @author Carzer
     * Date: 2019-07-02
     */
    public boolean setObject(K key, V value) {
        SessionCallback<V> sessionCallback = new SessionCallback<>() {
            @Override
            public List<V> execute(RedisOperations redisOperations) {
                redisOperations.multi();
                redisOperations.opsForValue().set(RedisConstants.REDIS_PREFIX + key, value);
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
     * @author Carzer
     * Date: 2019-07-02
     */
    public boolean setObject(K key, V value, long time) {
        SessionCallback<V> sessionCallback = new SessionCallback<V>() {
            @Override
            public List<V> execute(RedisOperations redisOperations) {
                redisOperations.multi();
                redisOperations.opsForValue().set(RedisConstants.REDIS_PREFIX + key, value, time, TimeUnit.SECONDS);

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
     * @author Carzer
     * Date: 2019-07-02
     */
    public boolean setObjectList(Map<K, V> map) {
        if (CollectionUtils.isEmpty(map)) {
            return false;
        } else {
            Map<K, V> setMap = new ConcurrentHashMap<>(map.size());
            for (Map.Entry<K, V> kvEntry : map.entrySet()) {
                setMap.put((K) (RedisConstants.REDIS_PREFIX + kvEntry.getKey()), kvEntry.getValue());
            }
            SessionCallback<V> sessionCallback = new SessionCallback<>() {
                @Override
                public List<V> execute(RedisOperations redisOperations) {
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
     * @author Carzer
     * Date: 2019-07-02
     */
    public V getObject(K key) {
        SessionCallback<V> sessionCallback = new SessionCallback<>() {
            @Override
            public V execute(RedisOperations redisOperations) {
                redisOperations.multi();
                redisOperations.opsForValue().get(RedisConstants.REDIS_PREFIX + key);
                List<V> v = redisOperations.exec();
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
     * @author Carzer
     * Date: 2019-07-02
     */
    public List<V> getObjectList(K pattern) {
        Set<K> keys = redisTemplate.keys((K) (RedisConstants.REDIS_PREFIX + pattern));
        SessionCallback<List<V>> sessionCallback = new SessionCallback<>() {
            @Override
            public List<V> execute(RedisOperations redisOperations) {
                redisOperations.multi();
                redisOperations.opsForValue().multiGet(keys);
                List<V> v = redisOperations.exec();
                List<V> list = new ArrayList<>();
                if (!CollectionUtils.isEmpty(v)) {
                    list.addAll((List<V>) v.get(0));
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
     * @author Carzer
     * Date: 2019-07-02
     */
    public boolean deleteObject(K key) {
        SessionCallback<V> sessionCallback = new SessionCallback<>() {
            @Override
            public List<V> execute(RedisOperations operations) {
                operations.multi();
                operations.delete(RedisConstants.REDIS_PREFIX + key);
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
     * @author Carzer
     * Date: 2019-07-02
     */
    public boolean deleteObjectList(K pattern) {
        Set<K> keys = redisTemplate.keys((K) (RedisConstants.REDIS_PREFIX + pattern));
        SessionCallback<V> sessionCallback = new SessionCallback<>() {
            @Override
            public List<V> execute(RedisOperations operations) {
                operations.multi();
                operations.delete(keys);
                return operations.exec();
            }
        };
        redisTemplate.execute(sessionCallback);
        return true;
    }

}

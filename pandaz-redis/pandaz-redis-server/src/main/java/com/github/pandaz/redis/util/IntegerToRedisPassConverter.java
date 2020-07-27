package com.github.pandaz.redis.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.stereotype.Component;

/**
 * redis数字密码转换
 *
 * @author Carzer
 * @since 2020-07-27
 */
@Component
@ReadingConverter
public class IntegerToRedisPassConverter implements Converter<Integer, RedisPassword> {

    /**
     * 转换方法
     *
     * @param source source
     * @return 密码
     */
    @Override
    public RedisPassword convert(Integer source) {
        return RedisPassword.of(source.toString());
    }
}

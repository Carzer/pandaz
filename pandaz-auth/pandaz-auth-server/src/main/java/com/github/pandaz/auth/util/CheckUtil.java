package com.github.pandaz.auth.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pandaz.commons.entity.BaseEntity;
import com.github.pandaz.commons.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * 编码检查工具
 *
 * @author Carzer
 * @since 2019-11-25
 */
@Component
@Slf4j
public class CheckUtil<E extends BaseEntity, M extends BaseMapper<E>> {

    /**
     * 私有构造方法
     */
    private CheckUtil() {

    }

    /**
     * 检查编码是否存在，如果编码为空，则自动生成新编码
     * 使用前需校验实体是否为空
     *
     * @param entity   检查实体
     * @param mapper   mapper
     * @param errorMsg 如果编码存在时的提示信息
     * @return java.lang.String
     */
    public String checkOrSetCode(E entity, M mapper, String errorMsg) {
        return checkOrSetCode(entity, mapper, errorMsg, null, null);
    }

    /**
     * 检查编码是否存在，如果编码为空，则自动生成新编码
     * 使用前需校验实体是否为空
     *
     * @param entity   检查实体
     * @param mapper   mapper
     * @param errorMsg 如果编码存在时的提示信息
     * @param prefix   id前缀
     * @param suffix   id后缀
     * @return java.lang.String
     */
    public String checkOrSetCode(E entity, M mapper, String errorMsg, String prefix, String suffix) {
        // 指定查询列
        String declaredCode = "code";
        // 最终code
        String lastCode;
        // 通过反射获取code
        Field field = ReflectionUtils.findField(entity.getClass(), declaredCode);
        if (field == null) {
            throw new IllegalArgumentException(String.format("[%s]实体无[%s]属性", entity.getClass(), declaredCode));
        } else {
            Object code = ReflectionUtils.getField(field, entity);
            // 如果设置了code，则查询是否已存在，若已存在则返回错误
            if (!StringUtils.isEmpty(code) && StringUtils.hasText(code.toString())) {
                lastCode = code.toString();
                QueryWrapper<E> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(declaredCode, code);

                // 如果根据编码查询的结果大于0，则抛出错误
                if (mapper.selectCount(queryWrapper) > 0) {
                    throw new IllegalArgumentException(errorMsg);
                }
            }
            // 如果未设置code，则根据前缀、后缀生成code
            else {
                lastCode = UuidUtil.getUuid();
                lastCode = StringUtils.hasText(prefix) ? String.format("%s%s", prefix, lastCode) : lastCode;
                lastCode = StringUtils.hasText(suffix) ? String.format("%s%s", lastCode, suffix) : lastCode;
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, entity, lastCode);
            }
        }
        return lastCode;
    }
}

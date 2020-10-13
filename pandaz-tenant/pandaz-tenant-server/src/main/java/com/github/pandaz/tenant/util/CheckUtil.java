package com.github.pandaz.tenant.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pandaz.commons.entity.BaseEntity;
import com.github.pandaz.commons.mapper.BasisMapper;
import com.github.pandaz.commons.util.UuidUtil;
import org.springframework.util.StringUtils;

/**
 * 编码检查工具
 *
 * @author Carzer
 * @since 2019-11-25
 */
public class CheckUtil {

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
     * @param errorMsg 如果编码存在时的提示信息
     * @return java.lang.String
     */
    public static <M extends BasisMapper<E>, E extends BaseEntity> String checkOrSetCode(M mapper, E entity, String errorMsg) {
        return checkOrSetCode(mapper, entity, errorMsg, null, null);
    }

    /**
     * 检查编码是否存在，如果编码为空，则自动生成新编码
     * 使用前需校验实体是否为空
     *
     * @param entity   检查实体
     * @param errorMsg 如果编码存在时的提示信息
     * @param prefix   id前缀
     * @param suffix   id后缀
     * @return java.lang.String
     */
    public static <M extends BasisMapper<E>, E extends BaseEntity> String checkOrSetCode(M mapper, E entity, String errorMsg, String prefix, String suffix) {
        // 最终code
        String lastCode;
        String code = entity.getCode();
        // 如果设置了code，则查询是否已存在，若已存在则返回错误
        if (StringUtils.hasText(code)) {
            lastCode = code;
            QueryWrapper<E> queryWrapper = new QueryWrapper<>();
            /*
                因为mybatis-plus的问题，暂时无法使用lambda表达式来设置列
                2020年9月25日
             */
            queryWrapper.eq("code", code);
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
            entity.setCode(lastCode);
        }
        return lastCode;
    }
}

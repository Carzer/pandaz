package com.pandaz.usercenter.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.commons.entity.BaseEntity;
import com.pandaz.commons.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * pandaz:com.pandaz.usercenter.util
 * <p>
 * 编码检查工具
 *
 * @author Carzer
 * @since 2019-11-25
 */
@Component
@Slf4j
public class CheckUtils<E extends BaseEntity, M extends BaseMapper<E>> {

    /**
     * 私有构造方法
     */
    private CheckUtils() {

    }

    /**
     * 检查编码是否存在，如果编码为空，则自动生成新编码
     *
     * @param entity   检查实体
     * @param mapper   mapper
     * @param errorMsg 如果编码存在时的提示信息
     * @param prefix   id前缀
     * @param suffix   id后缀
     * @return java.lang.String
     */
    public String checkOrSetCode(E entity, M mapper, String errorMsg, String prefix, String suffix) {
        //指定查询列
        String declaredCode = "code";
        //最终code
        String lastCode = "";
        try {
            //通过反射获取code
            Field field = entity.getClass().getDeclaredField(declaredCode);
            field.setAccessible(true);
            String code = field.get(entity).toString();
            //如果设置了code，则查询是否已存在，若已存在则返回错误
            if (StringUtils.hasText(code)) {
                QueryWrapper<E> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(declaredCode, code);
                List<E> list = mapper.selectList(queryWrapper);
                if (!CollectionUtils.isEmpty(list)) {
                    throw new IllegalArgumentException(errorMsg);
                }
                lastCode = code;
            }
            //如果未设置code，则根据前缀、后缀生成code
            else {
                lastCode = UuidUtil.getUuid();
                lastCode = StringUtils.hasText(prefix) ? String.format("%s%s", prefix, lastCode) : lastCode;
                lastCode = StringUtils.hasText(suffix) ? String.format("%s%s", lastCode, suffix) : lastCode;
                field.set(entity, lastCode);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error(e.getMessage());
        }
        return lastCode;
    }
}

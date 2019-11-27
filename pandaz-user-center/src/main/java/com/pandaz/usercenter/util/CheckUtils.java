package com.pandaz.usercenter.util;

import com.pandaz.commons.entity.BaseEntity;
import com.pandaz.commons.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * pandaz:com.pandaz.usercenter.util
 * <p>
 * 编码检查工具
 *
 * @author Carzer
 * Date: 2019-11-25 14:04
 */
@Component
@Slf4j
public class CheckUtils<E extends BaseEntity, M> {

    /**
     * 检查编码是否存在，如果编码为空，则自动生成新编码
     *
     * @param entity   检查实体
     * @param mapper   mapper
     * @param errorMsg 如果编码存在时的提示信息
     * @param prefix   id前缀
     * @param suffix   id后缀
     * @return java.lang.String
     * @author Carzer
     * Date: 2019/11/25 15:41
     */
    public String checkOrSetCode(E entity, M mapper, String errorMsg, String prefix, String suffix) {
        String newCode;
        try {
            Field field = entity.getClass().getDeclaredField("code");
            field.setAccessible(true);
            Object code = field.get(entity);
            if (code == null) {
                newCode = UuidUtil.getUuid();
                newCode = StringUtils.hasText(prefix) ? prefix + newCode : newCode;
                newCode = StringUtils.hasText(suffix) ? newCode + suffix : newCode;
                field.set(entity, newCode);
            } else {
                newCode = code.toString();
                Method method = mapper.getClass().getDeclaredMethod("findByCode", String.class);
                Object temp = method.invoke(mapper, newCode);
                Assert.isNull(temp, errorMsg);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return newCode;
    }
}

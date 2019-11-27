package com.pandaz.commons.util;

import com.pandaz.commons.dto.BaseDTO;
import com.pandaz.commons.entity.BaseEntity;
import org.springframework.beans.BeanUtils;

/**
 * pandaz:com.pandaz.commons.util
 * <p>
 * dto转换
 *
 * @author Carzer
 * Date: 2019-10-24 13:28
 */
public final class SimpleClassConverter {

    /**
     * 构造方法
     */
    private SimpleClassConverter() {

    }

    /**
     * 转换方法
     *
     * @param s s
     * @param t t
     * @author Carzer
     * Date: 2019/10/25 09:07
     */
    public static <S extends BaseEntity, T extends BaseDTO> void convert(S s, T t) {
        BeanUtils.copyProperties(s, t);
    }

    /**
     * 回转方法
     *
     * @param t t
     * @param s s
     * @author Carzer
     * Date: 2019/10/25 09:07
     */
    public static <T extends BaseDTO, S extends BaseEntity> void reverse(T t, S s) {
        BeanUtils.copyProperties(t, s);
    }
}

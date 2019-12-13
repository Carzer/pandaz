package com.pandaz.commons;

import org.springframework.beans.factory.annotation.Value;

/**
 * pandaz:com.pandaz.commons
 * <p>
 * 基础mapper
 *
 * @author Carzer
 * @date 2019-11-25 15:49
 */
public interface BaseMapper<T> {

    /**
     * 根据编码查询
     *
     * @param code	编码
     * @return T
     * @author Carzer
     * @date 2019/11/25 15:50
     */
    T findByCode(@Value("code") String code);
}

package com.pandaz.commons.util;

import lombok.Getter;
import lombok.Setter;

/**
 * 执行结果
 *
 * @author Carzer
 * @since 2019-09-03
 */
@Setter
@Getter
public final class ExecuteResult<T> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 设置data
     *
     * @param data data
     */
    public void setData(T data) {
        this.success = true;
        this.data = data;
    }
}

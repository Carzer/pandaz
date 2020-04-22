package com.pandaz.commons.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 执行结果
 *
 * @author Carzer
 * @since 2019-09-03
 */
@Setter
@Getter
public final class Result<T> {

    /**
     * build 方法
     *
     * @return 返回成功
     */
    public static <T extends Serializable> Result<T> buildSuccess() {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        return result;
    }

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

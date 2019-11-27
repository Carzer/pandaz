package com.pandaz.commons.util;

import java.io.Serializable;

/**
 * pandaz:com.pandaz.commons.util
 * <p>
 * 执行结果
 *
 * @author Carzer
 * Date: 2019-09-03 10:21
 */
public final class ExecuteResult<T> implements Serializable {

    private static final long serialVersionUID = -4081885107988007568L;

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
     * 获取success
     *
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置success
     *
     * @param success success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 获取error
     *
     * @return error
     */
    public String getError() {
        return error;
    }

    /**
     * 设置error
     *
     * @param error error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * 获取data
     *
     * @return data
     */
    public T getData() {
        return data;
    }

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

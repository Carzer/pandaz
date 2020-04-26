package com.pandaz.commons.util;

import com.pandaz.commons.code.ICode;
import com.pandaz.commons.code.RCode;
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
public final class R<T> {

    /**
     * 编码
     */
    private long code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 构造方法
     *
     * @param iCode code
     */
    public R(ICode iCode) {
        this(iCode, null);
    }

    /**
     * 构造方法（成功）
     *
     * @param data data
     */
    public R(T data) {
        this(RCode.SUCCESS, data);
    }

    /**
     * 构造方法
     *
     * @param iCode code
     * @param data  data
     */
    public R(ICode iCode, T data) {
        this.code = iCode.getCode();
        this.message = iCode.getMessage();
        this.data = data;
    }

    /**
     * success 方法
     *
     * @return 返回成功
     */
    public static <T extends Serializable> R<T> success() {
        return new R<>(RCode.SUCCESS);
    }

    /**
     * fail 方法
     *
     * @return 返回失败
     */
    public static <T extends Serializable> R<T> fail() {
        return new R<>(RCode.FAILED);
    }

    /**
     * fail 方法
     *
     * @return 返回失败
     */
    public static R<String> fail(String message) {
        return new R<>(RCode.FAILED, message);
    }
}

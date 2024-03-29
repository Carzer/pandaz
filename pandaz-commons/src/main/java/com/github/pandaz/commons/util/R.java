package com.github.pandaz.commons.util;

import com.github.pandaz.commons.code.ICode;
import com.github.pandaz.commons.code.RCode;
import io.swagger.annotations.ApiModelProperty;
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
@SuppressWarnings({"unused", "AlibabaClassNamingShouldBeCamel"})
public final class R<T> {

    /**
     * 编码
     */
    @ApiModelProperty("编码：1000,操作成功")
    private long code;

    /**
     * 错误信息
     */
    @ApiModelProperty("错误信息")
    private String message;

    /**
     * 返回数据
     */
    @ApiModelProperty("返回数据")
    private T data;

    /**
     * 私有构造方法
     * <p>
     * 猜测由于feign的调用，产生的option请求
     * 如果没有空的构造方法，会因ICode而报空指针错误
     */
    private R() {
    }

    /**
     * 构造方法
     *
     * @param iCode {@link ICode}
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
     * @param iCode {@link ICode}
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
     * @param <T> t
     * @return 返回成功
     */
    public static <T extends Serializable> R<T> success() {
        return new R<>(RCode.SUCCESS);
    }

    /**
     * success 方法
     *
     * @param t   t
     * @param <T> t
     * @return 返回成功
     */
    public static <T extends Serializable> R<T> success(T t) {
        return new R<>(t);
    }

    /**
     * fail 方法
     *
     * @param <T> t
     * @return 返回失败
     */
    public static <T extends Serializable> R<T> fail() {
        return new R<>(RCode.FAILED);
    }

    /**
     * fail 方法
     *
     * @param t   t
     * @param <T> t
     * @return 返回失败
     */
    public static <T extends Serializable> R<T> fail(T t) {
        return new R<>(RCode.FAILED, t);
    }

    /**
     * fail 方法
     *
     * @param message message
     * @return 返回失败
     */
    public static R<String> fail(String message) {
        return new R<>(RCode.FAILED, message);
    }

    /**
     * 成功
     *
     * @return 成功
     */
    public boolean succeed() {
        return RCode.SUCCESS.getCode() == this.code;
    }

    @Override
    public String toString() {
        return String.format("[返回码:%s,返回信息:%s]", this.code, this.message);
    }
}

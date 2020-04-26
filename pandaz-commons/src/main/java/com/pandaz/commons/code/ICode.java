package com.pandaz.commons.code;

/**
 * 返回码
 *
 * @author Carzer
 * @since 2020-04-26
 */
public interface ICode {

    /**
     * 操作编码
     *
     * @return 操作编码
     */
    long getCode();

    /**
     * 操作描述
     *
     * @return 操作描述
     */
    String getMessage();
}

package com.github.pandaz.commons.exception;

import com.github.pandaz.commons.code.FileCode;
import lombok.Getter;

/**
 * 文件服务异常
 *
 * @author Carzer
 * @since 2020-05-12
 */
public class FileException extends AbstractCustomException {

    /**
     * 文件服务码
     */
    @Getter
    private final FileCode code;

    /**
     * 构造方法
     *
     * @param code 文件服务码
     */
    public FileException(FileCode code) {
        this.code = code;
    }
}

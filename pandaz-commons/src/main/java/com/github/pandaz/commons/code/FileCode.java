package com.github.pandaz.commons.code;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件服务编码
 *
 * @author Carzer
 * @since 2020-05-12
 */
@SuppressWarnings("unused")
public enum FileCode implements ICode {
    /**
     * 操作码
     */
    SUCCESS(10000, "操作成功"),
    FAIL(10001, "操作失败"),
    FTP_CLIENT_CONNECT_FAIL(10002, "建立FTP连接异常"),
    GET_CLIENT_FROM_POOL_FAIL(10003, "从连接池获取client异常"),
    FILE_EXISTED(10004, "文件已存在"),
    FILE_NOT_EXISTED(10005, "文件不存在"),
    DIR_NOT_EXISTED(10006, "文件夹不存在"),
    ORIGIN_FILE_LARGER(10007, "目标文件更大,停止操作");

    /**
     * 编码
     */
    @Getter
    private final long code;

    /**
     * 信息
     */
    @Getter
    private final String message;

    /**
     * 所有枚举集合
     */
    private static final Map<Long, FileCode> ENUMS = new HashMap<>();

    /*
      静态方法
     */
    static {
        for (FileCode fileCode : FileCode.values()) {
            ENUMS.put(fileCode.getCode(), fileCode);
        }
    }

    /**
     * 构造方法
     *
     * @param code    编码
     * @param message 信息
     */
    FileCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据编码获取枚举
     *
     * @param code 编码
     * @return 枚举
     */
    public static FileCode getEnum(Long code) {
        return ENUMS.get(code);
    }
}
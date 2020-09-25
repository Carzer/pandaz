package com.github.pandaz.commons.util;

import java.util.List;
import java.util.stream.Collectors;

/**
 * List操作相关的工具类
 *
 * @author Carzer
 * @since 2020-09-25
 */
public class ListUtil {

    /**
     * 私有构造方法
     */
    private ListUtil() {

    }

    /**
     * 获取source比target多出的元素
     *
     * @param source 源
     * @param target 目标
     * @return 多出的元素
     */
    public static <T> List<T> more(List<T> source, List<T> target) {
        return source.stream().filter(code -> !(target.contains(code))).collect(Collectors.toList());
    }
}

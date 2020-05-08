package com.github.pandaz.commons.util;

import java.util.List;

/**
 * 通用工具类
 *
 * @author Carzer
 * @since 2020-04-27
 */
public class CommonUtils {

    /**
     * 私有构造方法
     */
    private CommonUtils() {

    }

    /**
     * 判断list中是否存在value,忽略大小写
     *
     * @param list  list
     * @param value value
     * @return 结果
     */
    public static boolean containsIgnoreCase(List<String> list, String value) {
        for (String str : list) {
            if (str.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}

package com.pandaz.commons.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 类转换器
 *
 * @author Carzer
 * @since 2019-12-20
 */
@Slf4j
public class BeanCopyUtil {

    /**
     * 私有构造方法
     */
    private BeanCopyUtil() {

    }

    /**
     * 使用缓存提高效率
     */
    private static final ConcurrentHashMap<String, BeanCopier> COPIER_CACHE = new ConcurrentHashMap<>();

    /**
     * 复制list
     *
     * @param sourceList  sourceList
     * @param targetClass targetClass
     * @param <S>         源类型
     * @param <T>         目标类型
     * @return 目标类list
     */
    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream().map(source -> copy(source, targetClass)).collect(Collectors.toList());
    }

    /**
     * 复制并新建类
     *
     * @param source      source
     * @param targetClass targetClass
     * @param <S>         源类型
     * @param <T>         目标类型
     * @return 目标类
     */
    public static <S, T> T copy(S source, Class<T> targetClass) {
        T instance = null;
        try {
            instance = targetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("创建对象异常:{}", e.getMessage());
        }
        return copy(source, instance);
    }

    /**
     * 复制方法
     *
     * @param source source
     * @param target target
     * @param <S>    源类型
     * @param <T>    目标类型
     * @return 目标类
     */
    public static <S, T> T copy(S source, T target) {
        try {
            String baseKey = generateKey(source.getClass(), target.getClass());
            BeanCopier copier;
            if (COPIER_CACHE.containsKey(baseKey)) {
                copier = COPIER_CACHE.get(baseKey);
            } else {
                copier = BeanCopier.create(source.getClass(), target.getClass(), false);
                COPIER_CACHE.put(baseKey, copier);
            }
            copier.copy(source, target, null);
        } catch (Exception e) {
            log.error("复制对象属性异常:{}", e.getMessage());
        }
        return target;
    }

    /**
     * 将分页结果转换为map
     *
     * @param page  分页结果
     * @param clazz 目标类型
     * @param <S>   源类型
     * @param <T>   目标类型
     * @return 转换结果
     */
    public static <S, T> HashMap<String, Object> convertToMap(IPage<S> page, Class<T> clazz) {
        Assert.notNull(page, "传入的page不能为空！");
        HashMap<String, Object> map = new HashMap<>(5);
        map.put("pageNum", page.getCurrent());
        map.put("pageSize", page.getSize());
        map.put("total", page.getTotal());
        map.put("pages", page.getPages());
        map.put("record", copyList(page.getRecords(), clazz));
        return map;
    }

    /**
     * 生成缓存key
     *
     * @param source source
     * @param target target
     * @return 目标类
     */
    private static String generateKey(Class<?> source, Class<?> target) {
        return String.format("%s|%s", source.toString(), target.toString());
    }

}


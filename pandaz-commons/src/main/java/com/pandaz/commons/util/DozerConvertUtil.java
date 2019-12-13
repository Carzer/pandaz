package com.pandaz.commons.util;

import com.github.pagehelper.Page;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * pandaz:com.pandaz.commons.util
 * <p>
 * Dozer转换类
 *
 * @author Carzer
 * @date 2019-10-28
 */
public final class DozerConvertUtil {

    /**
     * 构造方法
     */
    private DozerConvertUtil() {

    }

    /**
     * mapper
     */
    private static Mapper mapper = new DozerBeanMapper();

    /**
     * 转换实体类
     *
     * @param source source
     * @param clazz  target class
     * @return T
     * @author Carzer
     * @date 2019/10/28 14:34
     */
    public static <S, T> T convert(S source, Class<T> clazz) {
        return source == null ? null : mapper.map(source, clazz);
    }

    /**
     * 转换列表
     *
     * @param sourceList sourceList
     * @param clazz      clazz
     * @return java.util.List<T>
     * @author Carzer
     * @date 2019/10/28 14:44
     */
    public static <S, T> List<T> convertList(List<S> sourceList, Class<T> clazz) {
        List<T> es = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sourceList)) {
            sourceList.forEach(source -> es.add(convert(source, clazz)));
        }
        return es;
    }

    /**
     * 将分页信息转换为返回map
     *
     * @param page  page
     * @param clazz clazz
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author Carzer
     * @date 2019/10/28 16:19
     */
    public static <S, T> Map<String, Object> convertToMap(Page<S> page, Class<T> clazz) {
        Assert.notNull(page, "传入的page不能为空！");
        Map<String, Object> map = new ConcurrentHashMap<>(5);
        map.put("pageNum", page.getPageNum());
        map.put("pageSize", page.getPageSize());
        map.put("total", page.getTotal());
        map.put("pages", page.getPages());
        map.put("record", convertList(page.getResult(), clazz));
        return map;
    }

    /**
     * 转换分页类
     *
     * @param page  page
     * @param clazz clazz
     * @return com.github.pagehelper.Page<T>
     * @author Carzer
     * @date 2019/10/28 14:47
     */
    public static <S, T> Page<T> convertPage(Page<S> page, Class<T> clazz) {
        if (page == null) {
            return null;
        }
        List<T> tList = convertList(page.getResult(), clazz);
        Page<T> tPage = simpleCopyPage(page);
        tPage.addAll(tList);
        return tPage;
    }

    /**
     * 将page的基本属性进行copy
     *
     * @param sourcePageObject page
     * @return com.github.pagehelper.Page<T>
     * @throws
     * @author Carzer
     * @date 2019/10/28 15:34
     */
    private static <S, T> Page<T> simpleCopyPage(Page<S> sourcePageObject) {
        Page<T> targetPageObject = new Page<>();
        targetPageObject.setPageNum(sourcePageObject.getPageNum());
        targetPageObject.setPageSize(sourcePageObject.getPageSize());
        targetPageObject.setStartRow(sourcePageObject.getStartRow());
        targetPageObject.setEndRow(sourcePageObject.getEndRow());
        targetPageObject.setTotal(sourcePageObject.getTotal());
        targetPageObject.setPages(sourcePageObject.getPages());
        targetPageObject.setReasonable(sourcePageObject.getReasonable());
        targetPageObject.setPageSizeZero(sourcePageObject.getPageSizeZero());
        return targetPageObject;
    }
}
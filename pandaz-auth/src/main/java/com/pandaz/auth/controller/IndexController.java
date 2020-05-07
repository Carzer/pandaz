package com.pandaz.auth.controller;

import com.pandaz.commons.util.R;
import com.pandaz.auth.client.RedisClient;
import com.pandaz.auth.client.UploadClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.security.Principal;
import java.util.*;

/**
 * 默认controller
 *
 * @author Carzer
 * @since 2019-07-17
 */
@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IndexController {

    /**
     * 上传客户端
     */
    private final UploadClient uploadClient;

    /**
     * 上下文环境
     */
    private final WebApplicationContext applicationContext;

    /**
     * Redis客户端
     */
    private final RedisClient redisClient;

    /**
     * home
     *
     * @param principal principal
     * @return 执行结果
     */
    @GetMapping("userInfo")
    public R<HashMap<String, Principal>> home(Principal principal) {
        HashMap<String, Principal> map = new HashMap<>(1);
        map.put("user", principal);
        return new R<>(map);
    }

    /**
     * 测试Redis服务
     *
     * @param value value
     * @return 执行结果
     */
    @GetMapping("testRedis")
    @PreAuthorize("hasRole('ADMIN')")
    public R<Object> testRedis(String value) {
        String key = redisClient.setObject("test", value, 0).getData();
        return redisClient.getObject(key);
    }

    /**
     * 获取所有mapping地址
     *
     * @return 执行结果
     */
    @GetMapping("getAllUrl")
    @PreAuthorize("hasRole('ADMIN')")
    public R<List<Map<String, String>>> getAllUrl() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<Map<String, String>> list = new ArrayList<>();
        map.forEach((info, method) -> {
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            Map<String, String> singleMapping = new HashMap<>(patterns.size() + methods.size() + 2);
            // 类名
            singleMapping.put("className", method.getMethod().getDeclaringClass().getName());
            // 方法名
            singleMapping.put("method", method.getMethod().getName());
            // url
            patterns.forEach(url -> singleMapping.put("url", url));
            // 请求方式
            methods.forEach(requestMethod -> singleMapping.put("type", requestMethod.toString()));
            list.add(singleMapping);
        });
        return new R<>(list);
    }


    /**
     * 上传方法
     *
     * @param file file
     * @return java.lang.String
     */
    @PostMapping("upload")
    public String upload(MultipartFile file) {
        return uploadClient.handleFileUpload(file);
    }
}

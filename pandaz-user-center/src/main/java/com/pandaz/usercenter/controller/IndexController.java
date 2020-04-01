package com.pandaz.usercenter.controller;

import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.usercenter.client.RedisClient;
import com.pandaz.usercenter.client.UploadClient;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
     * @return com.pandaz.commons.util.ExecuteResult<java.security.Principal>
     */
    @GetMapping("userInfo")
    @PreAuthorize("hasRole('ADMIN')")
    public ExecuteResult<HashMap<String, Principal>> home(Principal principal) {
        ExecuteResult<HashMap<String, Principal>> result = new ExecuteResult<>();
        HashMap<String, Principal> map = new HashMap<>(1);
        map.put("user", principal);
        result.setData(map);
        return result;
    }

    /**
     * 测试Redis服务
     *
     * @param value value
     * @return com.pandaz.commons.util.ExecuteResult<java.lang.String>
     */
    @GetMapping("testRedis")
    @PreAuthorize("hasRole('ADMIN')")
    public ExecuteResult<String> testRedis(String value) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            String key = redisClient.setRedisValue(value).getData();
            ExecuteResult<String> executeResult = redisClient.getRedisValue(key);
            if (executeResult.isSuccess()) {
                result.setData(executeResult.getData());
            } else {
                result.setError(executeResult.getError());
            }
        } catch (Exception e) {
            log.error("测试异常", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 获取所有mapping地址
     *
     * @return com.pandaz.commons.util.ExecuteResult<java.util.List>
     */
    @GetMapping("getAllUrl")
    @PreAuthorize("hasRole('ADMIN')")
    public ExecuteResult<ArrayList<Map<String, String>>> getAllUrl() {
        ExecuteResult<ArrayList<Map<String, String>>> result = new ExecuteResult<>();
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        ArrayList<Map<String, String>> list = new ArrayList<>();
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
        result.setData(list);
        return result;
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

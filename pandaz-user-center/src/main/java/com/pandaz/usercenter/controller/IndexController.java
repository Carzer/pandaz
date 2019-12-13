package com.pandaz.usercenter.controller;

import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.usercenter.client.RedisClient;
import com.pandaz.usercenter.client.UploadClient;
import com.pandaz.usercenter.custom.properties.CustomProperties;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * pandaz:com.pandaz.usercenter.controller
 * <p>
 * 默认controller
 *
 * @author Carzer
 * @date 2019-07-17 15:20
 */
@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IndexController {

    private final CustomProperties customProperties;

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
     * @author Carzer
     * @date 2019/10/28 13:46
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ExecuteResult<Principal> home(Principal principal) {
        ExecuteResult<Principal> result = new ExecuteResult<>();
        result.setData(principal);
        return result;
    }

    /**
     * 测试Redis服务
     *
     * @param value value
     * @return com.pandaz.commons.util.ExecuteResult<java.lang.String>
     * @author Carzer
     * @date 2019/10/28 13:46
     */
    @GetMapping("/testRedis")
    public ExecuteResult<String> testRedis(String value) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            String key = redisClient.setRedisValue(value).getData();
            String v = redisClient.getRedisValue(key).getData();
            result.setData(v);
        } catch (Exception e) {
            log.error("测试出错了", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 获取所有mapping地址
     *
     * @return com.pandaz.commons.util.ExecuteResult<java.util.List>
     * @author Carzer
     * @date 2019/10/28 13:46
     */
    @GetMapping("/getAllUrl")
    public List getAllUrl() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<Map<String, String>> list = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {

            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();

            String className = method.getMethod().getDeclaringClass().getName();
            if (!className.startsWith(customProperties.getProjectPackage())) {
                continue;
            }
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            //map初始大小
            int initialCapacity = patterns.size() + methods.size() + 2;
            //结果map
            Map<String, String> singleMap = new ConcurrentHashMap<>(initialCapacity);
            //类名
            singleMap.put("className", className);
            // 方法名
            singleMap.put("method", method.getMethod().getName());
            //URL
            for (String url : patterns) {
                singleMap.put("url", url);
            }
            for (RequestMethod requestMethod : methods) {
                singleMap.put("type", requestMethod.toString());
            }
            list.add(singleMap);
        }
        return list;
    }

    /**
     * 上传方法
     *
     * @param file file
     * @return java.lang.String
     * @author Carzer
     * @date 2019/10/28 13:47
     */
    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        return uploadClient.handleFileUpload(file);
    }
}

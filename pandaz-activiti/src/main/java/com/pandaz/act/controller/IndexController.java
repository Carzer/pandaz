package com.pandaz.act.controller;

import com.pandaz.commons.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * 默认controller
 *
 * @author Carzer
 * @since 2019-07-18
 */
@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IndexController {

    /**
     * 服务信息
     */
    private final DiscoveryClient discoveryClient;

    /**
     * 上下文环境
     */
    private final WebApplicationContext applicationContext;

    @GetMapping("/dc")
    public String dc() {
        String services = String.format("Services: %s", discoveryClient.getServices());
        log.info(services);
        return services;
    }

    /**
     * 获取所有mapping地址
     *
     * @return 执行结果
     */
    @GetMapping("/getAllUrl")
    public Result<List<Map<String, String>>> getAllUrl() {
        Result<List<Map<String, String>>> result = new Result<>();
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
        result.setData(list);
        return result;
    }
}

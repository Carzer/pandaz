package com.pandaz.act.controller;

import com.pandaz.commons.util.ExecuteResult;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * pandaz:com.pandaz.act.controller
 * <p>
 * 默认controller
 *
 * @author Carzer
 * @date 2019-07-18 15:32
 */
@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IndexController {

    private final DiscoveryClient discoveryClient;

    /**
     * 上下文环境
     */
    private final WebApplicationContext applicationContext;

    @GetMapping("/dc")
    public String dc() {
        String services = "Services: " + discoveryClient.getServices();
        log.info(services);
        return services;
    }

    /**
     * 获取所有mapping地址
     *
     * @return com.pandaz.commons.util.ExecuteResult<java.util.List>
     * @author Carzer
     * @date 2019/10/28 13:46
     */
    @GetMapping("/getAllUrl")
    public ExecuteResult<List> getAllUrl() {
        ExecuteResult<List> result = new ExecuteResult<>();
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<Map<String, String>> list = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            Map<String, String> singleMapping = new ConcurrentHashMap<>(patterns.size() + methods.size() + 2);
            // 类名
            singleMapping.put("className", method.getMethod().getDeclaringClass().getName());
            // 方法名
            singleMapping.put("method", method.getMethod().getName());
            for (String url : patterns) {
                singleMapping.put("url", url);
            }
            for (RequestMethod requestMethod : methods) {
                singleMapping.put("type", requestMethod.toString());
            }

            list.add(singleMapping);
        }
        result.setData(list);
        return result;
    }
}

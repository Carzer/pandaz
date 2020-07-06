package com.github.pandaz.commons.controller;

import com.github.pandaz.commons.util.R;
import com.github.pandaz.commons.util.ServerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.annotations.ApiIgnore;

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
@ApiIgnore
public class IndexController {

    /**
     * 配置
     */
    private final ServerConfig serverConfig;

    /**
     * 上下文环境
     */
    private final WebApplicationContext applicationContext;

    /**
     * 服务信息
     */
    private final DiscoveryClient discoveryClient;

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
     * 发现注册的服务信息
     *
     * @return 服务信息
     */
    @GetMapping("/discovery")
    public String discovery() {
        String services = String.format("Services: %s", discoveryClient.getServices());
        log.info(services);
        return services;
    }

    /**
     * 应用启动后执行
     */
    public void onStartUp() {
        new RestTemplate().getForEntity(String.format("%s/wakeUp", serverConfig.getWakeUrl()), String.class);
    }

    /**
     * 首次访问url时，因为阿里sentinel的原因，会有一段时间的延迟
     * 所以在启动后主动访问一次应用
     */
    @GetMapping("wakeUp")
    public void wake() {
        log.debug("wake up a person who pretends to be asleep");
    }
}

package com.github.pandaz.auth.controller;

import com.github.pandaz.commons.util.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.annotations.ApiIgnore;

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
@ApiIgnore
public class IndexController {

    /**
     * 上下文环境
     */
    private final WebApplicationContext applicationContext;

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
}

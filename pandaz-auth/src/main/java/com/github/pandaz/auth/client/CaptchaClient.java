package com.github.pandaz.auth.client;

import com.github.pandaz.auth.client.fallback.CaptchaClientFallBackFactory;
import com.github.pandaz.auth.custom.interceptor.FeignOauth2RequestInterceptor;
import com.github.pandaz.commons.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 验证码服务
 *
 * @author Carzer
 * @since 2019-10-28
 */
@FeignClient(name = "${custom.client.redis-server}", fallbackFactory = CaptchaClientFallBackFactory.class, configuration = FeignOauth2RequestInterceptor.class)
@RequestMapping("/captcha")
public interface CaptchaClient {

    /**
     * 获取Redis value
     *
     * @param key key
     * @return 执行结果
     */
    @GetMapping
    R<Object> getObject(@RequestParam String key);

    /**
     * 设置Redis value
     *
     * @param key   key
     * @param value value
     * @return 执行结果
     */
    @PostMapping
    R<String> setObject(@RequestParam String key, @RequestParam String value);
}

package com.github.pandaz.redis.api;

import com.github.pandaz.commons.util.R;
import com.github.pandaz.redis.api.fallback.CaptchaApiFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 验证码服务
 *
 * @author Carzer
 * @since 2019-10-28
 */
@FeignClient(name = "pandaz-redis-server", path = "/captcha",
        fallbackFactory = CaptchaApiFallBackFactory.class)
public interface CaptchaApi {

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

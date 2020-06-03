package com.github.pandaz.redis.controller;

import com.github.pandaz.commons.util.R;
import com.github.pandaz.redis.service.RedisHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 验证码服务
 *
 * @author Carzer
 * @since 2020-05-14
 */
@Slf4j
@RestController
@RequestMapping("/captcha")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ApiIgnore
public class CaptchaController {

    /**
     * redis操作
     */
    private final RedisHelper redisHelper;

    /**
     * 获取Redis中验证码的计算结果
     *
     * @param key key
     * @return 执行结果
     */
    @GetMapping
    public R<Object> getCaptchaResult(String key) {
        return new R<>(redisHelper.getObject(key));
    }

    /**
     * 将验证码的计算结果放入Redis
     *
     * @param key   key
     * @param value value
     * @return 执行结果
     */
    @PostMapping
    public R<String> setCaptchaResult(String key, String value) {
        redisHelper.setObject(key, value, 180);
        return R.success();
    }
}

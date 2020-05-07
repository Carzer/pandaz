package com.pandaz.auth.service.impl;

import com.pandaz.commons.code.RCode;
import com.pandaz.commons.util.R;
import com.pandaz.auth.client.RedisClient;
import com.pandaz.auth.service.CaptchaService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码
 *
 * @author Carzer
 * @since 2020-04-27
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CaptchaServiceImpl implements CaptchaService {

    /**
     * 验证码存储key
     */
    private static final String CAPTCHA_KEY = "captcha:";

    /**
     * redis 缓存
     */
    private final RedisClient redisClient;

    /**
     * 生成验证码
     *
     * @param response response
     * @param key      key
     * @throws IOException IOException
     */
    @Override
    public void create(HttpServletResponse response, String key) throws IOException {
        if (!StringUtils.hasText(key)) {
            return;
        }
        setHeader(response);
        Captcha captcha = createCaptcha();
        redisClient.setObject(CAPTCHA_KEY + key, captcha.text(), 180);
        captcha.out(response.getOutputStream());
    }

    /**
     * 校验验证码
     *
     * @param key   前端上传 key
     * @param value 前端上传值
     * @return 是否成功
     */
    @Override
    public R<Boolean> check(String key, String value) {
        if (!StringUtils.hasText(value)) {
            return new R<>(RCode.VALID_CODE_EMPTY, false);
        }
        R<Object> result = redisClient.getObject(CAPTCHA_KEY + key);
        if (result.getData() == null) {
            return new R<>(RCode.VALID_CODE_EXPIRED, false);
        }
        if (!value.equalsIgnoreCase(result.getData().toString())) {
            return new R<>(RCode.VALID_CODE_ERROR, false);
        }
        return new R<>(true);
    }

    /**
     * 设置响应头
     *
     * @param response response
     */
    private void setHeader(HttpServletResponse response) {
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }

    /**
     * 创建验证码
     *
     * @return 验证码
     */
    private Captcha createCaptcha() {
        Captcha captcha = new ArithmeticCaptcha(115, 42);
        captcha.setCharType(2);
        return captcha;
    }
}

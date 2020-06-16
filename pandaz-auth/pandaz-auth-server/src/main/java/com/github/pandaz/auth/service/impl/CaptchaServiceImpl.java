package com.github.pandaz.auth.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.github.pandaz.auth.service.CaptchaService;
import com.github.pandaz.commons.code.RCode;
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
import java.util.concurrent.TimeUnit;

/**
 * 验证码
 *
 * @author Carzer
 * @since 2020-04-27
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuppressWarnings("unused")
public class CaptchaServiceImpl implements CaptchaService {

    /**
     * 验证码存储key
     */
    private static final String CAPTCHA_KEY = "pandaz:auth:captcha:";

    /**
     * 验证码缓存
     */
    @CreateCache(name = CAPTCHA_KEY, cacheType = CacheType.REMOTE, expire = 3, timeUnit = TimeUnit.MINUTES)
    private Cache<String, String> captchaCache;

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
        captchaCache.put(key, captcha.text());
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
    public RCode check(String key, String value) {
        if (!StringUtils.hasText(value)) {
            return RCode.VALID_CODE_EMPTY;
        }
        String text = captchaCache.get(key);
        if (!StringUtils.hasText(text)) {
            return RCode.VALID_CODE_EXPIRED;
        }
        if (!value.equalsIgnoreCase(text)) {
            return RCode.VALID_CODE_ERROR;
        }
        return RCode.SUCCESS;
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

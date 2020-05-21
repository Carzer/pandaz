package com.github.pandaz.auth.service.impl;

import com.github.pandaz.auth.client.CaptchaClient;
import com.github.pandaz.auth.service.CaptchaService;
import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.util.R;
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
    private final CaptchaClient captchaClient;

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
        captchaClient.setObject(CAPTCHA_KEY + key, captcha.text());
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
        R<Object> result = captchaClient.getObject(CAPTCHA_KEY + key);
        if (result.getData() == null) {
            return RCode.VALID_CODE_EXPIRED;
        }
        if (!value.equalsIgnoreCase(result.getData().toString())) {
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

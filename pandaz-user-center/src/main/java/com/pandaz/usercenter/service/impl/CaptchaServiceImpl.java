package com.pandaz.usercenter.service.impl;

import com.pandaz.commons.code.BizCode;
import com.pandaz.commons.exception.BizException;
import com.pandaz.commons.util.R;
import com.pandaz.usercenter.service.CaptchaService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码
 *
 * @author Carzer
 * @since 2020-04-27
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuppressWarnings("unchecked")
public class CaptchaServiceImpl implements CaptchaService {

    /**
     * 验证码存储key
     */
    private static final String CAPTCHA_KEY = "captcha";

    /**
     * 生成验证码
     *
     * @param request  request
     * @param response response
     * @param key      key
     * @throws IOException IOException
     */
    @Override
    public void create(HttpServletRequest request, HttpServletResponse response, String key) throws IOException {
        if (!StringUtils.hasText(key)) {
            throw new BizException(BizCode.VALID_CODE_EMPTY);
        }
        setHeader(response);
        Captcha captcha = createCaptcha();
        save(request, key, captcha.text());
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
    public R<Boolean> check(HttpServletRequest request, String key, String value) {
        if (!StringUtils.hasText(value)) {
            return new R<>(BizCode.VALID_CODE_EMPTY, false);
        }
        String str = get(request, key);
        remove(request, key);
        if (str == null) {
            return new R<>(BizCode.VALID_CODE_EXPIRED, false);
        }
        if (!value.equalsIgnoreCase(str)) {
            return new R<>(BizCode.VALID_CODE_ERROR, false);
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

    /**
     * 将内容存储到session中
     *
     * @param request request
     * @param key     key
     * @param value   value
     */
    private void save(HttpServletRequest request, String key, String value) {
        if (StringUtils.hasText(value)) {
            HttpSession session = request.getSession();
            HashMap<String, String> captchaMap;
            if (session.getAttribute(CAPTCHA_KEY) == null) {
                captchaMap = new HashMap<>(1);
            } else {
                captchaMap = (HashMap<String, String>) session.getAttribute(CAPTCHA_KEY);
                captchaMap.clear();
            }
            captchaMap.put(key, value);
            session.setAttribute(CAPTCHA_KEY, captchaMap);
        }
    }

    /**
     * 获取session中的内容
     *
     * @param request request
     * @param key     key
     * @return value
     */
    private String get(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        Map<String, String> captchaMap;
        if (session.getAttribute(CAPTCHA_KEY) == null) {
            return null;
        } else {
            captchaMap = (HashMap<String, String>) session.getAttribute(CAPTCHA_KEY);
            return captchaMap.get(key);
        }
    }

    /**
     * 清除相关内容
     *
     * @param request request
     * @param key     key
     */
    private void remove(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(CAPTCHA_KEY) != null) {
            HashMap<String, String> captchaMap = (HashMap<String, String>) session.getAttribute(CAPTCHA_KEY);
            captchaMap.remove(key);
            session.setAttribute(CAPTCHA_KEY, captchaMap);
        }
    }
}

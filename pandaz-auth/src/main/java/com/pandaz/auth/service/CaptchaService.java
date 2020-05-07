package com.pandaz.auth.service;

import com.pandaz.commons.util.R;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码服务
 *
 * @author Carzer
 * @since 2020-04-27
 */
public interface CaptchaService {

    /**
     * 生成验证码
     *
     * @param response response
     * @param key      key
     * @throws IOException IOException
     */
    void create(HttpServletResponse response, String key) throws IOException;

    /**
     * 校验验证码
     *
     * @param key   前端上传 key
     * @param value 前端上传值
     * @return 是否成功
     */
    R<Boolean> check(String key, String value);
}

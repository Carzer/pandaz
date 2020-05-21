package com.github.pandaz.auth.client.fallback;

import com.github.pandaz.auth.client.CaptchaClient;
import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.util.R;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * captcha fallback
 *
 * @author Carzer
 * @since 2019-10-28 10:23
 */
@Component
@Slf4j
public class CaptchaClientFallBackFactory implements FallbackFactory<CaptchaClient> {

    /**
     * 默认方法
     *
     * @param cause cause
     * @return 执行结果
     */
    @Override
    public CaptchaClient create(Throwable cause) {
        CaptchaClientFallBackFactory.log.error("fallback; reason was: {}", cause.getMessage());
        return new CaptchaClient() {
            @Override
            public R<Object> getObject(String key) {
                return new R<>(RCode.FAILED);
            }

            @Override
            public R<String> setObject(String key, String value) {
                return R.fail();
            }
        };
    }
}

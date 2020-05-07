package com.pandaz.auth.client.fallback;

import com.pandaz.auth.client.UploadClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 回调方法工厂类
 *
 * @author Carzer
 * @since 2019-07-26
 */
@Component
@Slf4j
public class UploadClientFallBackFactory implements FallbackFactory<UploadClient> {

    @Override
    public UploadClient create(Throwable cause) {
        UploadClientFallBackFactory.log.error("fallback; reason was: ", cause);
        return file -> "fallback";
    }
}

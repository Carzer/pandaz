package com.pandaz.usercenter.fallback;

import com.pandaz.usercenter.client.UploadClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * pandaz:com.pandaz.usercenter.fallback
 * <p>
 * 回调方法工厂类
 *
 * @author Carzer
 * @date 2019-07-26 10:52
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

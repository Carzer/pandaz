package com.pandaz.usercenter.client.fallback;

import com.pandaz.usercenter.client.DcClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 默认回调方法
 *
 * @author Carzer
 * @since 2019-07-22
 */
@Component
@Slf4j
public class DcClientFallBack implements DcClient {
    @Override
    public String consumer() {
        log.info("fallback");
        return "fallback";
    }

}

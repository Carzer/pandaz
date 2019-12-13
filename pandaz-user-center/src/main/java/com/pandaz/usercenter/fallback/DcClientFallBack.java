package com.pandaz.usercenter.fallback;

import com.pandaz.usercenter.client.DcClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * pandaz:com.pandaz.usercenter.fallback
 * <p>
 * 默认回调方法
 *
 * @author Carzer
 * @date 2019-07-22 09:54
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

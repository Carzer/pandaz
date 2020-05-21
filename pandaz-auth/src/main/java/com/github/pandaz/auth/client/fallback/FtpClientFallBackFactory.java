package com.github.pandaz.auth.client.fallback;

import com.github.pandaz.auth.client.FtpClient;
import com.github.pandaz.commons.util.R;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * ftp fallback
 *
 * @author Carzer
 * @since 2019-07-26
 */
@Component
@Slf4j
public class FtpClientFallBackFactory implements FallbackFactory<FtpClient> {

    /**
     * 默认方法
     *
     * @param cause cause
     * @return 执行结果
     */
    @Override
    public FtpClient create(Throwable cause) {
        FtpClientFallBackFactory.log.error("fallback; reason was: {}", cause.getMessage());
        return new FtpClient() {
            @Override
            public R<String> handleFileUpload(String pathname, String filename, MultipartFile file) {
                return R.fail();
            }

            @Override
            public R<String> handleFileDownload(String pathname, String filename, String originPath) {
                return R.fail();
            }

            @Override
            public R<String> handleFileDelete(String pathname, String filename) {
                return R.fail();
            }
        };
    }
}

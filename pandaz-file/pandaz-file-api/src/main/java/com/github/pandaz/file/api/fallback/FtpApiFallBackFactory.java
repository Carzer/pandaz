package com.github.pandaz.file.api.fallback;

import com.github.pandaz.commons.util.R;
import com.github.pandaz.file.api.FtpApi;
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
public class FtpApiFallBackFactory implements FallbackFactory<FtpApi> {

    /**
     * 默认方法
     *
     * @param cause cause
     * @return 执行结果
     */
    @Override
    public FtpApi create(Throwable cause) {
        FtpApiFallBackFactory.log.error("fallback; reason was: {}", cause.getMessage());
        return new FtpApi() {
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

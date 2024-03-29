package com.github.pandaz.file.api;

import com.github.pandaz.commons.util.R;
import com.github.pandaz.file.api.fallback.FtpApiFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 测试类
 *
 * @author Carzer
 * @since 2019-07-16
 */
@FeignClient(value = "pandaz-file-server", path = "/ftp",
        fallbackFactory = FtpApiFallBackFactory.class, configuration = MultipartSupportConfig.class)
public interface FtpApi {

    /**
     * 上传方法
     *
     * @param pathname 存储路径
     * @param filename 存储名称
     * @param file     文件
     * @return 路径
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<String> handleFileUpload(@RequestParam String pathname,
                               @RequestParam String filename,
                               @RequestPart(value = "file") MultipartFile file);

    /**
     * 下载方法
     *
     * @param pathname   存储路径
     * @param filename   文件名
     * @param originPath 下载路径
     * @return 执行结果
     */
    @GetMapping("/download")
    R<String> handleFileDownload(@RequestParam String pathname,
                                 @RequestParam String filename,
                                 @RequestParam String originPath);

    /**
     * 删除文件
     *
     * @param pathname 路径
     * @param filename 文件名
     * @return 执行结果
     */
    @DeleteMapping("/delete")
    R<String> handleFileDelete(@RequestParam String pathname,
                               @RequestParam String filename);
}
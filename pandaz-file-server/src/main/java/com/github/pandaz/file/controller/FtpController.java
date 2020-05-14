package com.github.pandaz.file.controller;

import com.github.pandaz.commons.util.R;
import com.github.pandaz.file.util.FtpUtil;
import com.github.pandaz.file.util.MultipartFileToFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 上传方法
 *
 * @author Carzer
 * @since 2019-07-18
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/ftp")
public class FtpController {

    /**
     * ftp 工具
     */
    private final FtpUtil ftpUtil;

    /**
     * 上传方法
     *
     * @param pathname 存储路径
     * @param filename 存储名称
     * @param file     文件
     * @return 路径
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<String> handleFileUpload(@RequestParam String pathname,
                                      @RequestParam String filename,
                                      @RequestPart(value = "file") MultipartFile file) {
        File dest = null;
        try {
            dest = MultipartFileToFile.transferTo(file);
            ftpUtil.uploadFile(pathname, filename, dest.getAbsolutePath());
            return new R<>(String.format("%s/%s", pathname, filename));
        } catch (Exception e) {
            log.error("上传文件异常：", e);
        } finally {
            if (dest != null) {
                try {
                    MultipartFileToFile.deleteTempFile(dest);
                } catch (IOException e) {
                    log.error("删除临时文件异常");
                }
            }
        }
        return R.fail();
    }

    /**
     * 下载方法
     *
     * @param pathname   存储路径
     * @param filename   文件名
     * @param originPath 下载路径
     * @return 执行结果
     */
    @GetMapping("/download")
    public R<String> handleFileDownload(@RequestParam String pathname,
                                        @RequestParam String filename,
                                        @RequestParam String originPath) {
        ftpUtil.downloadFile(pathname, filename, originPath);
        return R.success();
    }

    /**
     * 删除文件
     *
     * @param pathname 路径
     * @param filename 文件名
     * @return 执行结果
     */
    @DeleteMapping("/delete")
    public R<String> handleFileDelete(@RequestParam String pathname,
                                      @RequestParam String filename) {
        ftpUtil.deleteFile(pathname, filename);
        return R.success();
    }
}
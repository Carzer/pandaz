package com.pandaz.file.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * pandaz:com.pandaz.file.controller
 * <p>
 * 上传方法
 *
 * @author Carzer
 * @date 2019-07-18 15:32
 */
@RestController
public class UploadController {

    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String handleFileUpload(@RequestPart(value = "file") MultipartFile file) {
        return file.getOriginalFilename();
    }

}
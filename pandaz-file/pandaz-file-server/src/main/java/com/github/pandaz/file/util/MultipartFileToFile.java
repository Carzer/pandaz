package com.github.pandaz.file.util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

/**
 * MultipartFileToFile
 *
 * @author Carzer
 * @since 2020-05-12
 */
public class MultipartFileToFile {

    /**
     * 构造方法
     */
    private MultipartFileToFile() {

    }

    /**
     * MultipartFile 转 File
     *
     * @param file file
     * @throws IOException e
     */
    public static File transferTo(MultipartFile file) throws IOException {
        if (file == null || file.getSize() <= 0 || !StringUtils.hasText(file.getOriginalFilename())) {
            return null;
        } else {
            File toFile;
            try (InputStream ins = file.getInputStream()) {
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);
                return toFile;
            }
        }
    }

    /**
     * 删除本地临时文件
     *
     * @param file file
     * @throws IOException e
     */
    public static void deleteTempFile(File file) throws IOException {
        if (file != null) {
            Files.delete(file.toPath());
        }
    }

    /**
     * 获取流文件
     *
     * @param ins  ins
     * @param file file
     * @throws IOException e
     */
    private static void inputStreamToFile(InputStream ins, File file) throws IOException {
        try (OutputStream os = new FileOutputStream(file)) {
            int bytesRead;
            int len = 8192;
            byte[] buffer = new byte[len];
            while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
}


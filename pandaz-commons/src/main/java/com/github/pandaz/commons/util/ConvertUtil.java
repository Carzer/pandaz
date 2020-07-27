package com.github.pandaz.commons.util;

import com.github.pandaz.commons.constants.CommonConstants;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 转换工具类
 *
 * @author Carzer
 * @since 2020-07-24
 */
@SuppressWarnings({"unused", "AlibabaUndefineMagicConstant"})
public class ConvertUtil {

    /**
     * 私有构造方法
     */
    private ConvertUtil() {

    }

    /**
     * byte[] 转 InputStream
     *
     * @param buf byte[]
     * @return InputStream
     */
    public static InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    /**
     * InputStream 转 byte[]
     *
     * @param inputStream InputStream
     * @return byte[]
     * @throws IOException IOException
     */
    public static byte[] input2byte(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc;
        while ((rc = inputStream.read(buff, 0, CommonConstants.READ_BUFFER_LEN)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        return swapStream.toByteArray();
    }

    /**
     * InputStream 转 OutputStream
     *
     * @param in InputStream
     * @return OutputStream
     * @throws IOException Exception
     */
    public static ByteArrayOutputStream in2out(InputStream in) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream;
    }

    /**
     * OutputStream 转 InputStream
     *
     * @param out OutputStream
     * @return InputStream
     */
    public static ByteArrayInputStream out2in(OutputStream out) {
        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) out;
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    /**
     * 文件流转换成文件
     *
     * @param filepath filepath
     * @return 文件
     * @throws IOException Exception
     */
    public static File parseFile(MultipartFile filepath) throws IOException {
        if (filepath != null && StringUtils.hasText(filepath.getOriginalFilename())) {
            File file = new File(filepath.getOriginalFilename());
            FileUtils.copyInputStreamToFile(filepath.getInputStream(), file);
            return file;
        }
        return null;
    }
}  
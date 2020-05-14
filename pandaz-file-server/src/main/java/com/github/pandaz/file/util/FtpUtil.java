package com.github.pandaz.file.util;

import com.github.pandaz.commons.code.FileCode;
import com.github.pandaz.commons.exception.FileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ftp服务端
 *
 * @author Carzer
 * @since 2020-05-12
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FtpUtil {

    /**
     * ftpClient连接池
     */
    private final FtpClientPool ftpClientPool;

    /**
     * 上传文件
     *
     * @param pathname       ftp服务保存地址
     * @param fileName       上传到ftp的文件名
     * @param originFileName 待上传文件的名称（绝对地址）
     * @return 执行结果
     */
    public FileCode uploadFile(String pathname, String fileName, String originFileName) {
        FTPClient ftpClient = getFtpClient();
        try {
            if (enterDirectory(pathname, ftpClient)) {
                FTPFile[] ftpFiles = ftpClient.listFiles(encodeName(fileName));
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                if (ftpFiles.length > 0) {
                    long originSize = ftpFiles[0].getSize();
                    File localFile = new File(originFileName);
                    long localSize = localFile.length();
                    if (originSize == localSize) {
                        return FileCode.FILE_EXISTED;
                    } else if (originSize > localSize) {
                        return FileCode.ORIGIN_FILE_LARGER;
                    }
                    if (appendFile(encodeName(fileName), localFile, ftpClient, originSize)) {
                        return FileCode.SUCCESS;
                    }
                }
                try (InputStream inputStream = new FileInputStream(new File(originFileName))) {
                    if (ftpClient.storeFile(encodeName(fileName), inputStream)) {
                        return FileCode.SUCCESS;
                    }
                }
            }
        } catch (Exception e) {
            log.error("上传文件异常", e);
            throw new FileException(FileCode.FAIL);
        } finally {
            releaseFtpClient(ftpClient);
        }
        return FileCode.FAIL;
    }

    /**
     * 断点续传
     *
     * @param originFileName 上传到ftp的文件
     * @param localFile      待上传的文件
     * @param ftpClient      ftpClient
     * @param originSize     上传节点
     * @return 执行结果
     * @throws IOException e
     */
    private boolean appendFile(String originFileName, File localFile, FTPClient ftpClient, long originSize) throws IOException {
        try (OutputStream out = ftpClient.appendFileStream(encodeName(originFileName))) {
            try (RandomAccessFile raf = new RandomAccessFile(localFile, "r")) {
                ftpClient.setRestartOffset(originSize);
                raf.seek(originSize);
                byte[] bytes = new byte[1024];
                int c;
                while ((c = raf.read(bytes)) != -1) {
                    out.write(bytes, 0, c);
                }
                out.flush();
            }
        }
        return ftpClient.completePendingCommand();
    }

    /**
     * 下载文件
     *
     * @param pathname   FTP服务器文件目录
     * @param filename   文件名称
     * @param originPath 下载后的文件路径
     * @return 执行结果
     */
    public FileCode downloadFile(String pathname, String filename, String originPath) {
        FTPClient ftpClient = getFtpClient();
        boolean append = false;
        try {
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles(encodeName(filename));
            if (ftpFiles.length > 0) {
                long localSize = ftpFiles[0].getSize();
                File originFile = new File(originPath, ftpFiles[0].getName());
                if (originFile.exists()) {
                    long originSize = originFile.length();
                    if (originSize >= localSize) {
                        return FileCode.ORIGIN_FILE_LARGER;
                    }
                    ftpClient.setRestartOffset(originSize);
                    append = true;
                }
                try (OutputStream outputStream = new FileOutputStream(originFile, append)) {
                    if (ftpClient.retrieveFile(encodeName(ftpFiles[0].getName()), outputStream)) {
                        return FileCode.SUCCESS;
                    }
                }
            } else {
                return FileCode.FILE_NOT_EXISTED;
            }
        } catch (Exception e) {
            log.error("下载文件异常", e);
        } finally {
            releaseFtpClient(ftpClient);
        }
        return FileCode.FAIL;
    }

    /**
     * 删除文件
     *
     * @param pathname FTP服务器保存目录
     * @param filename 要删除的文件名称
     * @return 执行结果
     */
    public FileCode deleteFile(String pathname, String filename) {
        FTPClient ftpClient = getFtpClient();
        try {
            if (ftpClient.changeWorkingDirectory(pathname) && ftpClient.deleteFile(encodeName(filename))) {
                return FileCode.SUCCESS;
            } else {
                return FileCode.FILE_NOT_EXISTED;
            }
        } catch (Exception e) {
            log.error("删除文件异常", e);
        } finally {
            releaseFtpClient(ftpClient);
        }
        return FileCode.FAIL;
    }

    /**
     * 删除文件夹
     *
     * @param pathname 文件夹路径
     * @param dirName  文件夹名
     * @return 执行结果
     */
    public FileCode removeDirectory(String pathname, String dirName) {
        FTPClient ftpClient = getFtpClient();
        try {
            if (ftpClient.changeWorkingDirectory(pathname) && ftpClient.removeDirectory(dirName)) {
                return FileCode.SUCCESS;
            } else {
                return FileCode.DIR_NOT_EXISTED;
            }
        } catch (Exception e) {
            log.error("删除文件夹异常", e);
        } finally {
            releaseFtpClient(ftpClient);
        }
        return FileCode.FAIL;
    }

    /**
     * 按行读取FTP文件
     *
     * @param remoteFilePath 文件路径（path+fileName）
     * @return 文件内容
     * @throws IOException IOException
     */
    public List<String> readFileByLine(String remoteFilePath) throws IOException {
        FTPClient ftpClient = getFtpClient();
        try (InputStream in = ftpClient.retrieveFileStream(encodePath(remoteFilePath));
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            return br.lines().map(StringUtils::trimAllWhitespace)
                    .filter(StringUtils::hasText).collect(Collectors.toList());
        } finally {
            ftpClient.completePendingCommand();
            releaseFtpClient(ftpClient);
        }
    }

    /**
     * 获取指定路径下FTP文件
     *
     * @param remotePath 路径
     * @return FTPFile数组
     * @throws IOException IOException
     */
    public FTPFile[] retrieveFtpFiles(String remotePath) throws IOException {
        FTPClient ftpClient = getFtpClient();
        try {
            return ftpClient.listFiles(encodePath(remotePath + "/"),
                    file -> file != null && file.getSize() > 0);
        } finally {
            releaseFtpClient(ftpClient);
        }
    }

    /**
     * 获取指定路径下FTP文件名称
     *
     * @param remotePath 路径
     * @return ftp文件名称列表
     * @throws IOException IOException
     */
    public List<String> retrieveFileNames(String remotePath) throws IOException {
        FTPFile[] ftpFiles = retrieveFtpFiles(remotePath);
        if (ftpFiles == null || ftpFiles.length < 1) {
            return Collections.emptyList();
        }
        return Arrays.stream(ftpFiles).filter(Objects::nonNull)
                .map(FTPFile::getName).collect(Collectors.toList());
    }

    /**
     * 进入目录，如果不存在，则创建后进入
     *
     * @param remotePath 远程路径
     * @param ftpClient  ftpClient
     * @return 执行结果
     * @throws IOException IOException
     */
    private boolean enterDirectory(String remotePath, FTPClient ftpClient) throws IOException {
        if (!ftpClient.changeWorkingDirectory(remotePath)) {
            String[] paths = remotePath.split("/");
            StringBuilder pathBuilder = new StringBuilder();
            // 创建文件层级并进入
            for (String path : paths) {
                String subDirectory = encodeName(path);
                pathBuilder.append("/").append(subDirectory);
                if (!ftpClient.changeWorkingDirectory(pathBuilder.toString()) && !ftpClient.makeDirectory(subDirectory)) {
                    log.error("创建目录[" + subDirectory + "]异常");
                    return false;
                }
                ftpClient.changeWorkingDirectory(subDirectory);
            }
        }
        return true;
    }

    /**
     * 编码文件路径
     * FTP协议里面，规定文件名编码为iso-8859-1，所以目录名或文件名需要转码
     *
     * @param path 文件路径
     * @return 文件路径
     */
    private String encodePath(String path) {
        return new String(path.replace("//", "/").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }

    /**
     * 编码文件名
     * FTP协议里面，规定文件名编码为iso-8859-1，所以目录名或文件名需要转码
     *
     * @param fileName 文件名
     * @return 文件名
     */
    private String encodeName(String fileName) {
        return new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }

    /**
     * 获取ftpClient
     *
     * @return 执行结果
     */
    private FTPClient getFtpClient() {
        try {
            FTPClient ftpClient = ftpClientPool.get();
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory("/");
            return ftpClient;
        } catch (Exception e) {
            log.error("获取ftpClient异常：", e);
            throw new FileException(FileCode.GET_CLIENT_FROM_POOL_FAIL);
        }
    }

    /**
     * 释放ftpClient
     *
     * @param ftpClient ftpClient
     */
    private void releaseFtpClient(FTPClient ftpClient) {
        if (ftpClient != null) {
            try {
                ftpClientPool.release(ftpClient);
            } catch (Exception e) {
                log.error("释放ftpClient异常", e);
                if (ftpClient.isAvailable()) {
                    try {
                        ftpClient.disconnect();
                    } catch (IOException ioe) {
                        log.error("断开ftpClient异常:", ioe);
                    }
                }
            }
        }
    }
}
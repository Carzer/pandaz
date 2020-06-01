package com.github.pandaz.file.util;

import com.github.pandaz.commons.code.FileCode;
import com.github.pandaz.commons.exception.FileException;
import com.github.pandaz.file.config.ftp.FtpProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * FtpClient对象工厂类
 *
 * @author Carzer
 * @since 2020-05-12
 */
@Slf4j
public class FtpClientPooledObjectFactory implements PooledObjectFactory<FTPClient> {

    /**
     * 配置
     */
    private final FtpProperties ftpProperties;

    /**
     * 构造方法
     *
     * @param ftpProperties 配置
     */
    public FtpClientPooledObjectFactory(FtpProperties ftpProperties) {
        this.ftpProperties = ftpProperties;
    }

    /**
     * 创建连接
     *
     * @return ftpClient
     * @throws Exception 异常
     * @see FtpClientPool initPool(initialSize,maxIdle)
     */
    @Override
    public PooledObject<FTPClient> makeObject() throws Exception {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpProperties.getHost(), ftpProperties.getPort());
            ftpClient.login(ftpProperties.getUsername(), ftpProperties.getPassword());
            log.info("连接FTP服务器返回码{}", ftpClient.getReplyCode());
            ftpClient.setBufferSize(ftpProperties.getBufferSize());
            ftpClient.setControlEncoding(ftpProperties.getEncoding());
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            return new DefaultPooledObject<>(ftpClient);
        } catch (Exception e) {
            log.error("建立FTP连接失败", e);
            if (ftpClient.isAvailable()) {
                ftpClient.disconnect();
            }
            throw new FileException(FileCode.FTP_CLIENT_CONNECT_FAIL);
        }
    }

    /**
     * 销毁方法
     *
     * @param pooledObject pooledObject
     * @throws Exception 异常
     */
    @Override
    public void destroyObject(PooledObject<FTPClient> pooledObject) throws Exception {
        FTPClient ftpClient = pooledObject.getObject();
        if (ftpClient != null && ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
    }

    /**
     * 验证
     *
     * @param pooledObject pooledObject
     * @return 执行结果
     */
    @Override
    public boolean validateObject(PooledObject<FTPClient> pooledObject) {
        FTPClient ftpClient = pooledObject.getObject();
        if (ftpClient == null || !ftpClient.isConnected()) {
            return false;
        }
        try {
            ftpClient.changeWorkingDirectory("/");
            return true;
        } catch (Exception e) {
            log.error("验证FTP连接失败::{}", e.getMessage());
            return false;
        }
    }

    /**
     * 激活
     *
     * @param pooledObject pooledObject
     */
    @Override
    public void activateObject(PooledObject<FTPClient> pooledObject) {
        log.debug("activateObject");
    }

    /**
     * 取消初始化
     *
     * @param pooledObject pooledObject
     */
    @Override
    public void passivateObject(PooledObject<FTPClient> pooledObject) {
        log.debug("passivateObject");
    }
}
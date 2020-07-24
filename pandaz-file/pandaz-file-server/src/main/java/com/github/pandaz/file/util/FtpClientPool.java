package com.github.pandaz.file.util;

import com.github.pandaz.file.config.ftp.FtpProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * FtpClient连接池
 *
 * @author Carzer
 * @since 2020-05-12
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FtpClientPool {

    /**
     * ftpClient连接池
     */
    private ObjectPool<FTPClient> pool;

    /**
     * 配置
     */
    private final FtpProperties ftpProperties;

    /**
     * 获取
     *
     * @return ftpClient
     */
    public FTPClient get() {
        int retries = ftpProperties.getRetries();
        for (int i = 0; i < retries; i++) {
            try {
                return pool.borrowObject();
            } catch (Exception e) {
                log.error("从连接池获取client异常：", e);
            }
        }
        return null;
    }

    /**
     * 释放
     *
     * @param ftpClient ftpClient
     * @throws Exception 异常
     */
    public void release(FTPClient ftpClient) throws Exception {
        pool.returnObject(ftpClient);
    }

    /**
     * 创建方法
     */
    @PostConstruct
    private void construct() {
        if (ftpProperties.isOpen()) {
            GenericObjectPoolConfig<FTPClient> poolConfig = new GenericObjectPoolConfig<>();
            poolConfig.setTestOnBorrow(ftpProperties.isTestOnBorrow());
            poolConfig.setTestOnReturn(ftpProperties.isTestOnReturn());
            poolConfig.setTestWhileIdle(ftpProperties.isTestWhileIdle());
            poolConfig.setMinEvictableIdleTimeMillis(ftpProperties.getMinEvictableIdleTimeMillis());
            poolConfig.setSoftMinEvictableIdleTimeMillis(ftpProperties.getSoftMinEvictableIdleTimeMillis());
            poolConfig.setTimeBetweenEvictionRunsMillis(ftpProperties.getTimeBetweenEvictionRunsMillis());
            poolConfig.setMaxIdle(ftpProperties.getMaxIdle());
            pool = new GenericObjectPool<>(new FtpClientPooledObjectFactory(ftpProperties), poolConfig);
            initPool(ftpProperties.getInitialSize(), poolConfig.getMaxIdle());
        }
    }

    /**
     * 销毁方法
     */
    @PreDestroy
    private void destroy() {
        if (pool != null) {
            pool.close();
            log.debug("销毁ftpClientPool...");
        }
    }

    /**
     * 预先加载FTPClient连接到对象池中
     * <p>
     * 当执行pool.addObject方法时，实际上是执行了{@link GenericObjectPool#addObject()} 方法，
     * 当中会进行create <code>{
     * final PooledObject<T> p = create()
     * }</code>
     * 而create方法中执行了p = factory.makeObject()，也就是
     * {@link FtpClientPooledObjectFactory#makeObject()}
     *
     * @param initialSize 初始化连接数
     * @param maxIdle     最大空闲连接数
     */
    private void initPool(Integer initialSize, int maxIdle) {
        if (initialSize == null || initialSize <= 0) {
            return;
        }
        int size = Math.min(initialSize, maxIdle);
        for (int i = 0; i < size; i++) {
            try {
                pool.addObject();
            } catch (Exception e) {
                log.error("preLoadingFtpClient error...", e);
            }
        }
    }
}
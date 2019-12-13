package com.pandaz.usercenter.config.datasource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 数据源配置
 *
 * @author carzer
 * @date 2019/12/13
 */
@Configuration
@EnableScheduling
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataSourceConfig {

    /**
     * 数据源配置
     */
    private final DBProperties properties;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        //按照目标数据源名称和目标数据源对象的映射存放在Map中
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put("db0", properties.getDb0());
        targetDataSources.put("db1", properties.getDb1());
        //采用AbstractRoutingDataSource的对象包装多数据源
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(properties.getDb0());
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
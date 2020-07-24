package com.github.pandaz.auth.config.datasource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置
 *
 * @author Carzer
 * @since 2019-12-13
 */
@Configuration
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataSourceConfig {

    /**
     * 数据源配置
     */
    private final DataSourceProperties properties;

    /**
     * 数据源
     *
     * @return 数据源
     */
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        // 按照目标数据源名称和目标数据源对象的映射存放在Map中
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put("ds0", properties.getDs0());
        targetDataSources.put("ds1", properties.getDs1());
        // 采用AbstractRoutingDataSource的对象包装多数据源
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(properties.getDs0());
        return dataSource;
    }

    /**
     * 事务管理器
     *
     * @param dataSource 数据源
     * @return 事务管理器
     */
    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
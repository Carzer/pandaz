package com.pandaz.usercenter.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * Description: 数据源配置
 *
 * @author carzer
 * @date 2019/12/13
 */
@Component
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DBProperties {

    /**
     * db0
     */
    @NestedConfigurationProperty
    private HikariDataSource db0;

    /**
     * db1
     */
    @NestedConfigurationProperty
    private HikariDataSource db1;


}
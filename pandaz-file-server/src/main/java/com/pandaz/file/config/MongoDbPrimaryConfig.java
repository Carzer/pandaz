package com.pandaz.file.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * mongoDB配置
 *
 * @author Carzer
 * @since 2019-07-16
 */
@Configuration
@AutoConfigureAfter(MongoDbPrimaryConfig.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoDbPrimaryConfig {

    /**
     * mongoDB配置
     */
    private final MongoDbPrimaryProperty mongoDbPrimaryProperty;

    /**
     * 注入mongodb的工厂类
     * <p>
     *
     * @return org.springframework.data.mongodb.MongoDbFactory
     */
    @Primary
    @Bean(name = "mongoDbPrimaryFactory")
    public MongoDbFactory mongoDbPrimaryFactory() {
        return MongoDbFactoryHelper.getMongoDbFactory(mongoDbPrimaryProperty.getHost(), mongoDbPrimaryProperty.getUser(), mongoDbPrimaryProperty.getPwd(), mongoDbPrimaryProperty.getName());
    }

    /**
     * 获取操作实例
     *
     * @param mongoDbPrimaryFactory factory
     * @return org.springframework.data.mongodb.core.MongoTemplate
     */
    @Primary
    @Bean(name = "mongoPrimaryTemplate")
    public MongoTemplate mongoPrimaryTemplate(@Qualifier("mongoDbPrimaryFactory") MongoDbFactory mongoDbPrimaryFactory) {
        return new MongoTemplate(mongoDbPrimaryFactory);
    }

    /**
     * 文件操作
     *
     * @param mongoDbPrimaryFactory factory
     * @param mongoPrimaryTemplate  template
     * @return org.springframework.data.mongodb.gridfs.GridFsTemplate
     */
    @Bean(name = "gridFsPrimaryTemplate")
    public GridFsTemplate gridFsPrimaryTemplate(@Qualifier("mongoDbPrimaryFactory") MongoDbFactory mongoDbPrimaryFactory, @Qualifier("mongoPrimaryTemplate") MongoTemplate mongoPrimaryTemplate) {
        return new GridFsTemplate(mongoDbPrimaryFactory, mongoPrimaryTemplate.getConverter());
    }
}

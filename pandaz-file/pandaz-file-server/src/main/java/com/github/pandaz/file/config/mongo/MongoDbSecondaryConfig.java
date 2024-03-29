package com.github.pandaz.file.config.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * mongoDB配置
 *
 * @author Carzer
 * @since 2019-07-16
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoDbSecondaryConfig {

    /**
     * mongoDB配置
     */
    private final MongoDbSecondaryProperty mongoDbSecondaryProperty;

    /**
     * 注入mongodb的工厂类
     *
     * @return org.springframework.data.mongodb.MongoDbFactory
     */
    @Bean(name = "mongoDbSecondaryFactory")
    public MongoDatabaseFactory mongoDbSecondaryFactory() {
        return MongoDbFactoryHelper.getMongoDbFactory(mongoDbSecondaryProperty.getUri());
    }

    /**
     * 获取操作实例
     *
     * @param mongoDbSecondaryFactory factory
     * @return org.springframework.data.mongodb.core.MongoTemplate
     */
    @Bean(name = "mongoSecondaryTemplate")
    public MongoTemplate mongoSecondaryTemplate(@Qualifier("mongoDbSecondaryFactory") MongoDatabaseFactory mongoDbSecondaryFactory) {
        return new MongoTemplate(mongoDbSecondaryFactory);
    }

    /**
     * 文件操作
     *
     * @param mongoDbSecondaryFactory factory
     * @param mongoSecondaryTemplate  template
     * @return org.springframework.data.mongodb.gridfs.GridFsTemplate
     */
    @Bean(name = "gridFsSecondaryTemplate")
    public GridFsTemplate gridFsSecondaryTemplate(@Qualifier("mongoDbSecondaryFactory") MongoDatabaseFactory mongoDbSecondaryFactory, @Qualifier("mongoSecondaryTemplate") MongoTemplate mongoSecondaryTemplate) {
        return new GridFsTemplate(mongoDbSecondaryFactory, mongoSecondaryTemplate.getConverter());
    }
}

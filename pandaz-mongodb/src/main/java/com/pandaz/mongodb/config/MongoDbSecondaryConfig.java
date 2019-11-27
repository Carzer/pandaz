package com.pandaz.mongodb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * mongoDB配置
 *
 * @author Carzer
 * Date: 2019-07-16
 */
@Configuration
@AutoConfigureAfter(MongoDbSecondaryConfig.class)
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
     * @author Carzer
     * Date: 2019-07-16 15:14
     */
    @Bean(name = "mongoDbSecondaryFactory")
    public MongoDbFactory mongoDbSecondaryFactory() {
        return MongoDbFactoryHelper.getMongoDbFactory(mongoDbSecondaryProperty.getHost(), mongoDbSecondaryProperty.getUser(), mongoDbSecondaryProperty.getPwd(), mongoDbSecondaryProperty.getName());
    }

    /**
     * 获取操作实例
     *
     * @param mongoDbSecondaryFactory factory
     * @return org.springframework.data.mongodb.core.MongoTemplate
     * @author Carzer
     * Date: 2019-07-16 15:15
     */
    @Bean(name = "mongoSecondaryTemplate")
    public MongoTemplate mongoSecondaryTemplate(@Qualifier("mongoDbSecondaryFactory") MongoDbFactory mongoDbSecondaryFactory) {
        return new MongoTemplate(mongoDbSecondaryFactory);
    }

    /**
     * 文件操作
     *
     * @param mongoDbSecondaryFactory factory
     * @param mongoSecondaryTemplate  template
     * @return org.springframework.data.mongodb.gridfs.GridFsTemplate
     * @author Carzer
     * Date: 2019-07-16 15:15
     */
    @Bean(name = "gridFsSecondaryTemplate")
    public GridFsTemplate gridFsSecondaryTemplate(@Qualifier("mongoDbSecondaryFactory") MongoDbFactory mongoDbSecondaryFactory, @Qualifier("mongoSecondaryTemplate") MongoTemplate mongoSecondaryTemplate) {
        return new GridFsTemplate(mongoDbSecondaryFactory, mongoSecondaryTemplate.getConverter());
    }

}

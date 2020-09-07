package com.github.pandaz.file.config.mongo;

import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * mongodb工具
 *
 * @author Carzer
 * @since 2019-07-16
 */
class MongoDbFactoryHelper {

    private MongoDbFactoryHelper() {

    }

    /**
     * 获取MongoDB工厂类
     *
     * @param uri 连接uri
     * @return 工厂类
     */
    static MongoDatabaseFactory getMongoDbFactory(String uri) {
        // uri格式mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
        // 为了方便实现mongodb多数据库和数据库的负载均衡这里使用url方式创建工厂
        return new SimpleMongoClientDatabaseFactory(uri);
    }
}

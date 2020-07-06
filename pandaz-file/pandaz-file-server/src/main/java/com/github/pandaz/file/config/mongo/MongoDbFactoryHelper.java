package com.github.pandaz.file.config.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.util.StringUtils;

/**
 * mongodb工具
 *
 * @author Carzer
 * @since 2019-07-16
 */
@SuppressWarnings("java:S1133")
class MongoDbFactoryHelper {

    private MongoDbFactoryHelper() {

    }

    /**
     * 获取MongoDB工厂类
     *
     * @param host MongoDB地址
     * @param user 用户名
     * @param pwd  密码
     * @param name 库名
     * @return org.springframework.data.mongodb.MongoDbFactory
     * @see #getMongoDbFactory(String)}
     * @deprecated (since v0.3, mongodb集群采用uri方式直连)
     */
    @Deprecated(since = "v0.3", forRemoval = true)
    static MongoDbFactory getMongoDbFactory(String host, String user, String pwd, String name) {
        // uri格式mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("mongodb://");
        if (StringUtils.hasText(user)) {
            uriBuilder.append(user).append(':').append(pwd).append('@');
        }
        uriBuilder.append(host).append("/?slaveOk=true;wtimeoutMS=60000;connectTimeoutMS=10000;socketTimeoutMS=65000;waitQueueTimeoutMS=60000");
        // 为了方便实现mongodb多数据库和数据库的负载均衡这里使用url方式创建工厂
        MongoClient mongoClient = MongoClients.create(uriBuilder.toString());
        return new SimpleMongoClientDbFactory(mongoClient, name);
    }

    /**
     * 获取MongoDB工厂类
     *
     * @param uri 连接uri
     * @return 工厂类
     */
    static MongoDbFactory getMongoDbFactory(String uri) {
        // uri格式mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
        // 为了方便实现mongodb多数据库和数据库的负载均衡这里使用url方式创建工厂
        return new SimpleMongoClientDbFactory(uri);
    }
}

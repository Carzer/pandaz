package com.pandaz.file.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.util.StringUtils;

/**
 * pandaz:com.pandaz.mongodb.config
 * <p>
 * mongodb工具
 *
 * @author Carzer
 * @date 2019-07-16
 */
class MongoDbFactoryHelper {

    private MongoDbFactoryHelper() {

    }

    /**
     * 获取MongoDB配置
     *
     * @param host MongoDB地址
     * @param user 用户名
     * @param pwd  密码
     * @param name 库名
     * @return org.springframework.data.mongodb.MongoDbFactory
     * @author Carzer
     * @date 2019-07-16 15:12
     */
    static MongoDbFactory getMongoDbFactory(String host, String user, String pwd, String name) {
        // uri格式mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("mongodb://");
        if (StringUtils.hasText(user)) {
            uriBuilder.append(user).append(':').append(pwd).append('@');
        }
        //todo 参数写入配置文件
        uriBuilder.append(host).append("/?slaveOk=true;wtimeoutMS=60000;connectTimeoutMS=10000;socketTimeoutMS=65000;waitQueueTimeoutMS=60000");
        // 为了方便实现mongodb多数据库和数据库的负载均衡这里使用url方式创建工厂
        MongoClientURI uri = new MongoClientURI(uriBuilder.toString());
        MongoClient mongoClient = new MongoClient(uri);
        return new SimpleMongoDbFactory(mongoClient, name);
    }
}

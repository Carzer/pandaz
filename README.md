# ![pandaz](https://github.com/Carzer/pandaz/blob/master/logo.png) <sup><sup>PandaZ</sup></sup>

前端：[PandaZ-UI](https://github.com/Carzer/pandaz-ui.git)

----------------------------------------------------
## 一、 简介

基于Java11、Spring Boot 2.1.7、Spring Cloud Greenwich.RELEASE的常用微服务集合

- 使用[Nacos](https://github.com/alibaba/nacos/releases)作为服务注册中心、分布式配置中心
- 使用[Feign](https://spring.io/projects/spring-cloud-openfeign)消费微服务
- 使用[Sentinel](https://github.com/alibaba/Sentinel/releases)进行流量控制、熔断降级
- 使用[Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)作为网关


## 二、 容器、数据库、中间件虚拟环境

- ~~提供已搭建好的CentOS7虚拟机(VMware)~~ 
    
    ~~百度云盘： https://pan.baidu.com/s/1GYXQ5E6rIOvDcXINu8Wrlg  提取码： myef ~~ <br/>
    ~~启动Nacos、Sentinel的命令路径：`/usr/local/src/startup.sh`，可自行修改为开机启动、或置于docker中~~    
    
- 搭建参考
    1. Docker：https://www.cnblogs.com/yufeng218/p/8370670.html
    
    2. MySQL：https://www.cnblogs.com/bigbrotherer/p/7241845.html
    
    3. Redis：https://blog.csdn.net/qq_27047215/article/details/91411559
    
    4. RabbitMQ：https://www.cnblogs.com/yufeng218/p/9452621.html <br/><br/>
    
     由于 https://hub.docker.com/ 非常容易超时，所以需要改一下docker镜像源： https://www.cnblogs.com/ming369/p/10711771.html<br/>
     个人镜像加速器地址：https://xvaz3vtq.mirror.aliyuncs.com


## 三、 微服务列表

### 1. [用户中心](http://localhost:9007)

    使用时，根据pandaz.sql更新数据库信息，默认用户：admin，密码：admin，默认逻辑删除

- [ ] **功能实现**
    - [x] 完成会话管理
    - [x] 完成多数据源动态切换功能（手动或注解）
    - [x] 基于Oauth2的单点功能
    - [x] feign调用服务时，传递token、sessionId
    - [ ] 微服务统一权限控制
    - [ ] 功能权限
    - [ ] UI开发
    - [ ] 数据权限
- [x] **框架集成**
    - [x] Spring Security
    - [x] Spring Session
    - [x] Spring Data Redis
    - [x] Spring Security Oauth2 
    - [x] Spring Security JWT
    
### 2. [Redis服务](http://localhost:9001)

- [ ] **功能实现**
    - [x] Redis基础服务
    - [ ] 多数据源
- [x] **框架集成**
    - [x] Spring Data Redis（使用Lettuce连接Sentinel集群）
    
### 3. [文件存储服务](http://localhost:9005)

- [ ] **功能实现**
    - [x] MongoDB基础服务
    - [x] MongoDB集群搭建、连接
    - [ ] 文件ftp服务集成
    - [ ] MongoDB、ftp统一服务
- [x] **框架集成**
    - [x] Spring Data Mongo
    
### 4. [定时任务](http://localhost:9003)

- [ ] **功能实现**
    - [ ] 定时任务统一调度
- [ ] **框架集成**
    - [ ] Quartz
    - [ ] LTS
    
### 5. [rabbitmq](http://localhost:9004)

- [ ] **功能实现**
    - [ ] 统一消息管理
    - [ ] 实现简单的分布式事务
- [x] **框架集成**
    - [x] Spring Cloud Stream
    
### 6. [审批流服务](http://localhost:9006)

- [ ] **功能实现**
    - [ ] 设计页面集成
    - [ ] 提供统一的流程服务
- [x] **框架集成**
    - [x] Activiti7
    
### 7. [API网关](http://localhost:7777)
主要的测试服务：[用户中心](http://localhost:9007)、[Redis服务](http://localhost:9001)

- [ ] **功能实现**
    - [x] 网关基础功能
    - [x] 整合oauth2
    - [ ] 动态路由
- [x] **框架集成**
    - [x] Spring Cloud Gateway
    - [x] Spring Security Oauth2
    
### 8. 未来

数据分析相关，或者机器学习相关？

要不来个表情包？

​    
## 四、 之前版本中遇到的问题（Eureka、Hystrix、Zuul）

> 印象笔记： https://app.yinxiang.com/fx/20e1570e-7d37-48ac-b79b-aac23b1bf952


## 五、 License 

[MIT](https://github.com/Carzer/pandaz/blob/master/LICENSE)

Copyright (c) 2020 Carzer
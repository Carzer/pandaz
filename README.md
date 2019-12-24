# This is PandaZ

    常用的微服务集合
    
----------------------------------------------------
## 一、 简介

基于Java11、Spring Boot 2.1.7、Spring Cloud Greenwich.RELEASE

- 使用[Nacos](https://github.com/alibaba/nacos/releases)作为服务注册中心、分布式配置中心
- 使用[Feign](https://spring.io/projects/spring-cloud-openfeign)消费微服务
- 使用[Sentinel](https://github.com/alibaba/Sentinel/releases)进行流量控制、熔断降级
- 使用[Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)作为网关

## 二、 容器、数据库、中间件搭建参考

1. Docker：https://www.cnblogs.com/yufeng218/p/8370670.html

2. MySQL：https://www.cnblogs.com/bigbrotherer/p/7241845.html

3. Redis：https://blog.csdn.net/qq_27047215/article/details/91411559

4. RabbitMQ：
   基本上是按照 https://www.cnblogs.com/yufeng218/p/9452621.html 中的步骤来的。  
   唯一的问题是 https://hub.docker.com/ 网站非常容易超时，所以需要改一下镜像源，修改方法如下  
   https://www.cnblogs.com/ming369/p/10711771.html

5. 另外，提供已搭建好的CentOS7虚拟机(VMware)

    百度云盘：
    提取码：
    
   虚拟机ip设定：
    ```
        进入
        /etc/sysconfig/network-scripts
        执行
        vi ifcfg-ens33
        设置BOOTPROTO为static
        添加
        IPADDR=172.16.2.130
        PREFIX=24
        GATEWAY=172.16.2.2
        DNS1=172.16.2.2
        NETMASK=255.255.255.0
        重启
        service network restart
    ```

## 三、 微服务列表

### 1. [用户中心](http://localhost:9007)

- [ ] **功能实现**
    - [x] 完成会话管理
    - [x] 完成多数据源动态切换功能（手动或注解）
    - [x] 基于Oauth2的单点功能
    - [ ] 功能权限
    - [ ] 前台UI开发
    - [ ] 数据权限
    
- [ ] **框架集成**
    - [x] Spring Security
    - [x] Spring Session
    - [x] Spring Data Redis
    - [x] Spring Security Oauth2 
    - [x] Spring Security JWT
    - [ ] ViewUI组件库
    
### 2. [Redis服务](http://localhost:9001)

- [x] **功能实现**
    - [x] Redis基础服务
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
    - [x] Quartz
    - [ ] LTS
    
### 5. [rabbitmq](http://localhost:9004)

- [ ] **功能实现**
    - [ ] 统一消息管理
    - [ ] 实现简单的分布式事务
- [x] **框架集成**
    - [x] Spring Cloud Stream
    
### 6. [审批流服务](http://localhost:9006)

- [ ] **功能实现**
    - [ ] 提供统一的流程服务
- [x] **框架集成**
    - [x] Activiti7
    
### 7. [API网关](http://localhost:7777)

- [ ] **功能实现**
    - [x] 网关基础功能
    - [ ] 整合oauth2
- [ ] **框架集成**
    - [x] Spring Cloud Gateway
    - [ ] Spring Security Oauth2
    
## 四、 之前版本中遇到的问题

> https://app.yinxiang.com/fx/20e1570e-7d37-48ac-b79b-aac23b1bf952
# This is PandaZ

    作者：Carzer
    邮箱：Carzer@hotmail.com
    版本：v1.1
    时间：2019年11月27日

## 一、 简介

基于Java11、SpringBoot 2.1.7、SpringCloud Greenwich.RELEASE

- 使用[Nacos](https://github.com/alibaba/nacos/releases)作为服务注册中心、分布式配置中心
- 使用Feign消费微服务
- 使用[Sentinel](https://github.com/alibaba/Sentinel/releases)作为断路器
- 使用Spring zuul作为网关(7777端口)

## 二、 容器、数据库、中间件搭建参考

1. Docker：https://www.cnblogs.com/yufeng218/p/8370670.html

2. MySQL：https://www.cnblogs.com/bigbrotherer/p/7241845.html

3. Redis：https://blog.csdn.net/qq_27047215/article/details/91411559

4. RabbitMQ：
   基本上是按照https://www.cnblogs.com/yufeng218/p/9452621.html中的步骤来的。  
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
    - [ ] 基于Oauth2的单点功能
    - [ ] 权限控制
    - [ ] 前台UI开发
    
- [ ] **框架集成**
    - [x] SpringSecurity
    - [x] SpringSession
    - [x] SpringDataRedis (使用Lettuce连接Sentinel集群)
    - [ ] SpringSecurityOauth2 
    - [ ] SpringSecurityJWT
    - [ ] ViewUI组件库（即iView4.0）
    
### 2. [Redis服务](http://localhost:9001)
- [x] **功能实现**
    - [x] Redis基础服务
- [x] **框架集成**
    - [x] SpringDataRedis（使用Lettuce连接Sentinel集群）
### 3. [MongoDB服务](http://localhost:9002)
- [ ] **功能实现**
    - [x] MongoDB基础服务
    - [ ] MongoDB集群搭建、连接
- [x] **框架集成**
    - [x] SpringDataMongo
### 4. [文件存储服务](http://localhost:9005)
    
### 5. [定时任务](http://localhost:9003)
- [ ] **功能实现**
    - [ ] 定时任务统一调度
- [ ] **框架集成**
    - [x] Quartz
    - [ ] LTS
### 6. [rabbitmq](http://localhost:9004)
- [ ] **功能实现**
    - [ ] 统一消息管理
    - [ ] 实现简单的分布式事务
- [x] **框架集成**
    - [x] SpringCloudStream
### 7. [审批流服务](http://localhost:9006)
- [ ] **功能实现**
    - [ ] 提供统一的流程服务
- [x] **框架集成**
    - [x] Activiti7

## 四、 v1.0中遇到的问题

> https://app.yinxiang.com/fx/20e1570e-7d37-48ac-b79b-aac23b1bf952
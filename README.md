# ![pandaz](./logo.png) <sup><sup>PandaZ</sup></sup>

前端：[PandaZ-UI](https://github.com/Carzer/pandaz-ui)

----------------------------------------------------
## 一、 简介

基于Java11、Spring Boot 2.1.7、Spring Cloud Greenwich.RELEASE的常用微服务集合

- 使用[Nacos](https://github.com/alibaba/nacos/releases)作为服务注册中心、分布式配置中心
- 使用[Feign](https://spring.io/projects/spring-cloud-openfeign)消费微服务
- 使用[Sentinel](https://github.com/alibaba/Sentinel/releases)进行流量控制、熔断降级
- 使用[Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)作为网关


## 二、 容器、数据库、中间件虚拟环境
  
- 搭建参考

推荐使用Docker直接搭建，搭建过程参考：[docker搭建过程参考](./docker搭建过程参考.md)


## 三、 微服务列表

### 1. [用户中心](http://localhost:9007)

    使用时，根据pandaz.sql更新数据库信息.使用InitTest初始化数据。
    默认用户：admin，密码：admin。测试oauth2客户端：test，密码：test。
    所有数据默认逻辑删除。
<details>
<summary>进行中...</summary>
<pre><code>
- [ ] **功能实现**
    - [x] 完成会话管理
    - [x] 完成多数据源动态切换功能（手动或注解）
    - [x] 基于Oauth2的单点功能
    - [x] feign调用服务时，传递token、sessionId
    - [x] UI集成（vue-admin-template）
    - [ ] 功能权限
    - [ ] 数据权限
    - [ ] 微服务统一权限控制
- [x] **框架集成**
    - [x] Spring Security
    - [x] Spring Session
    - [x] Spring Data Redis
    - [x] Spring Security Oauth2 
    - [x] Spring Security JWT
</code></pre>
</details>   

### 2. [Redis服务](http://localhost:9001)

<details>
<summary>进行中...</summary>
<pre><code>
- [ ] **功能实现**
    - [x] Redis基础服务
    - [ ] 多数据源
- [x] **框架集成**
    - [x] Spring Data Redis（使用Lettuce连接Sentinel集群）
</code></pre>
</details>  

### 3. [文件存储服务](http://localhost:9005)

<details>
<summary>进行中...</summary>
<pre><code>
- [ ] **功能实现**
    - [x] MongoDB基础服务
    - [x] MongoDB集群搭建、连接
    - [ ] 文件ftp服务集成
    - [ ] MongoDB、ftp统一服务
- [x] **框架集成**
    - [x] Spring Data Mongo
</code></pre>
</details> 

### 4. [定时任务](http://localhost:9003)

<details>
<summary>计划中...</summary>
<pre><code>
- [ ] **功能实现**
    - [ ] 定时任务统一调度
- [ ] **框架集成**
    - [ ] Quartz
    - [ ] LTS
</code></pre>
</details> 

### 5. [rabbitmq](http://localhost:9004)

<details>
<summary>进行中...</summary>
<pre><code>
- [ ] **功能实现**
    - [ ] 统一消息管理
    - [ ] 实现简单的分布式事务
- [x] **框架集成**
    - [x] Spring Cloud Stream
</code></pre>
</details> 

### 6. [审批流服务](http://localhost:9006)

<details>
<summary>进行中...</summary>
<pre><code>
- [ ] **功能实现**
    - [ ] 设计页面集成
    - [ ] 提供统一的流程服务
- [x] **框架集成**
    - [x] Activiti7
</code></pre>
</details> 

### 7. [API网关](http://localhost:7777)
主要的测试服务：[用户中心](http://localhost:9007)、[Redis服务](http://localhost:9001)

<details>
<summary>进行中...</summary>
<pre><code>
- [ ] **功能实现**
    - [x] 网关基础功能
    - [x] 整合oauth2
    - [ ] 动态路由
- [x] **框架集成**
    - [x] Spring Cloud Gateway
    - [x] Spring Security Oauth2
</code></pre>
</details> 

### 8. 其他
​    
## 四、 之前版本中遇到的问题（Eureka、Hystrix、Zuul）

> 印象笔记： https://app.yinxiang.com/fx/20e1570e-7d37-48ac-b79b-aac23b1bf952


## 五、 License 

[MIT](./LICENSE)

Copyright (c) 2020 Carzer
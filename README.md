# This is PandaZ

    作者：Carzer
    版本：v1.1
    时间：2019年11月27日

## 简介
基于Java11、spring boot 2.1.7、spring cloud Greenwich.RELEASE

- 使用Nacos作为服务注册中心、分布式配置中心
- 使用Feign消费微服务
- 使用Sentinel作为断路器
- 使用Spring zuul作为网关(7777端口)

## 微服务列表
### 1、用户中心(权限完善中)
    http://localhost:9007
- spring security
- spring session
- redis
### 2、redis服务(连接测试通过)
    http://localhost:9001
- spring redis （使用Lettuce连接）
### 3、MongoDB服务(待开发)
    http://localhost:9002
- MongoDB集群连接
### 4、文件存储服务(待开发)
    http://localhost:9005
### 5、定时任务(待开发)
    http://localhost:9003
- Quartz+LTS
### 6、rabbitmq(发送、接收测试通过)
    http://localhost:9004
- spring cloud stream
### 7、审批流服务(待开发)
    http://localhost:9006
- 使用activiti流程引擎
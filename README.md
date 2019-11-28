# This is PandaZ

    作者：Carzer
    版本：v1.1
    时间：2019年11月27日

## 简介

基于Java11、SpringBoot 2.1.7、SpringCloud Greenwich.RELEASE

- 使用[Nacos](https://github.com/alibaba/nacos/releases)作为服务注册中心、分布式配置中心
- 使用Feign消费微服务
- 使用[Sentinel](https://github.com/alibaba/Sentinel/releases)作为断路器
- 使用Spring zuul作为网关(7777端口)

## 容器、数据库、中间件搭建参考

1. Docker：https://www.cnblogs.com/yufeng218/p/8370670.html

2. MySQL：https://www.cnblogs.com/bigbrotherer/p/7241845.html

3. Redis：https://blog.csdn.net/qq_27047215/article/details/91411559

4. RabbitMQ：
   基本上是按照https://www.cnblogs.com/yufeng218/p/9452621.html中的步骤来的。  
   唯一的问题是https://hub.docker.com/网站非常容易超时，所以需要改一下镜像源，修改方法如下  
   https://www.cnblogs.com/ming369/p/10711771.html

另外，提供已搭建好的CentOS7虚拟机(VMware)，百度云盘：，提取码：。  
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

## 微服务列表

### 1、[用户中心](http://localhost:9007)(权限完善中)
- SpringSecurity
- SpringSession
- SpringDataRedis (使用Lettuce连接Sentinel集群)
```
flow
st=>start: 开始
op=>operation: My Operation
cond=>condition: Yes or No?
e=>end
st->op->cond
cond(yes)->e
cond(no)->op
&
```
### 2、[redis服务](http://localhost:9001)(连接测试通过)
- SpringDataRedis（使用Lettuce连接）
### 3、[MongoDB服务](http://localhost:9002)(待开发)
- MongoDB集群连接
### 4、[文件存储服务](http://localhost:9005)(待开发)
    
### 5、[定时任务](http://localhost:9003)(待开发)
- Quartz+LTS
### 6、[rabbitmq](http://localhost:9004)(发送、接收测试通过)
- SpringCloudStream
### 7、[审批流服务](http://localhost:9006)(待开发)
- 使用Activiti流程引擎

## v1.0中遇到的问题

> https://app.yinxiang.com/fx/20e1570e-7d37-48ac-b79b-aac23b1bf952
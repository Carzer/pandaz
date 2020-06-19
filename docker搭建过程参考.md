# 写在前面

超时问题：

由于 https://hub.docker.com/ 非常容易超时，所以需要改一下docker镜像源  
找到/etc/docker/daemon.json，添加  
>{ "registry-mirrors" :["https://docker.mirrors.ustc.edu.cn"]}  

>个人镜像加速器地址：https://xvaz3vtq.mirror.aliyuncs.com

如果高版本docker出现问题，可以使用其他方式：  
```shell 
curl -sSL https://get.daocloud.io/daotools/set_mirror.sh | sh -s http://f1361db2.m.daocloud   
```

docker0 IP地址修改方法：

```shell
vi /etc/docker/daemon.json
```
增加内容
>{  
 "registry-mirrors": ["https://xvaz3vtq.mirror.aliyuncs.com"] ,  
 "bip":"172.17.0.1/16"  
 }  
    
docker间互相访问时，可以使用172.17.0.1，而非localhost

调整时区：
```shell
cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
或
docker cp /etc/localtime [容器ID或者NAME]:/etc/localtime
```



# 搭建过程

## 0. 文件夹准备

```shell
mkdir -p [docker统一目录]/mysql/data
mkdir -p [docker统一目录]/mysql/conf
```
可能用到的配置文件（[docker统一目录]/mysql/conf）  
[docker.cnf](./conf/docker.cnf)  
[mysql.cnf](./conf/mysql.cnf)  
[mysqldump.cnf](./conf/mysqldump.cnf)  

```shell
mkdir -p [docker统一目录]/redis/conf
mkdir -p [docker统一目录]/redis/data
mkdir -p [docker统一目录]/redis/data/log
```
可能用到的配置文件（[docker统一目录]/redis/conf）  
[redis.conf](./conf/redis.conf)  

```shell
mkdir -p [docker统一目录]/rabbitmq/data
```
```shell
mkdir -p [docker统一目录]/mongo/data
```
```shell
mkdir -p [docker统一目录]/postgres/data
```
```shell
mkdir -p [docker统一目录]/sonarqube/logs
mkdir -p [docker统一目录]/sonarqube/data
mkdir -p [docker统一目录]/sonarqube/extensions
```
```shell
mkdir -p [docker统一目录]/nginx
mkdir -p [docker统一目录]/nginx/ext
mkdir -p [docker统一目录]/nginx/log
mkdir -p [docker统一目录]/nginx/html
```

可能用到的配置文件（[docker统一目录]/nginx）  
[nginx.conf](./conf/nginx.conf)  

```shell
mkdir -p [docker统一目录]/nacos/nacos
```

可能用到的配置文件（[docker统一目录]/nacos）  
[standalone-derby.yaml](./conf/standalone-derby.yaml)

```shell
mkdir -p [docker统一目录]/ftp/vsftpd
```

```shell
mkdir -p [docker统一目录]/jenkins/sh
mkdir -p [docker统一目录]/jenkins/jenkins_home
```

可能用到的配置文件（[docker统一目录]/jenkins/sh）  
[jenkins.sh](./conf/jenkins.sh) 

##  1. mysql

mysql选用5.7.29版本

```shell
docker pull mysql:5.7.29
```

```shell
docker run -p 3306:3306 --name mysql -v [docker统一目录]/mysql/data:/var/lib/mysql -v [docker统一目录]/mysql/conf:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=XApdgiFrv3#N -d mysql:5.7.29
```

```mysql
mysql -u root -p
XApdgiFrv3#N
CREATE USER 'pandaz'@'%' IDENTIFIED WITH mysql_native_password BY 'K0tXBGwNHBl#';
GRANT ALL PRIVILEGES ON *.* TO 'pandaz'@'%';
flush privileges;
```

## 2. redis

```shell
docker pull redis
```

```shell
docker run -d --privileged=true -p 6379:6379 -v [docker统一目录]/redis/conf/redis.conf:/etc/redis/redis.conf -v [docker统一目录]/redis/data:/data --name redis redis redis-server /etc/redis/redis.conf --appendonly yes
```

## 3. rabbitmq

```shell
docker pull rabbitmq:3.7.7-management
```

```shell
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 -v [docker统一目录]/rabbitmq/data:/var/lib/rabbitmq --hostname myRabbit -e RABBITMQ_DEFAULT_VHOST=my_vhost -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3.7.7-management
```

## 4. mongo

```shell
docker pull mongo
```

```shell
docker run --name mongo -p 27017:27017 -v [docker统一目录]/mongo/data:/data -d mongo --auth
```

```shell
use admin
db.createUser({ user:'admin',pwd:'123456',roles:[{role:'userAdminAnyDatabase',db:'admin'}]})
db.auth("admin","123456")
db.grantRolesToUser("admin",[{role:"dbOwner", db:"admin"}])
```

## 5.postgres

```shell
docker pull postgres
```

```shell
docker run -d --name postgres -p 5432:5432 -v [docker统一目录]/postgres/data:/var/lib/postgresql/data -e POSTGRES_USER=sonar -e POSTGRES_PASSWORD=sonar postgres
```

## 6.sonarqube

8.2+

sonarqube7.9以后，不再对mysql提供支持，所以搭配了postgres使用
```shell
docker pull sonarqube
```

```shell
docker run -d --name sonar -p 9000:9000 -p 9092:9092 -v [docker统一目录]/sonarqube/logs:/opt/sonarqube/logs -v [docker统一目录]/sonarqube/data:/opt/sonarqube/data -v [docker统一目录]/sonarqube/extensions:/opt/sonarqube/extensions -e SONARQUBE_JDBC_USERNAME=sonar -e SONARQUBE_JDBC_PASSWORD=sonar -e SONARQUBE_JDBC_URL=jdbc:postgresql://172.17.0.1:5432/sonar sonarqube
```


## 7.nginx

```shell
docker pull nginx
```

```shell
docker run --name nginx -p 8090:8090 -v [docker统一目录]/nginx/nginx.conf:/etc/nginx/nginx.conf -v [docker统一目录]/nginx/ext:/etc/nginx/ext -v [docker统一目录]/nginx/log:/var/log/nginx -v [docker统一目录]/nginx/html:/etc/nginx/html -d nginx
```

## 8.nacos&sentinel

```shell
git clone --depth 1 https://github.com/nacos-group/nacos-docker.git
cd nacos-docker
```
将standalone-derby.yaml文件复制到`[docker统一目录]/nacos`
修改内容为：

```yaml
version: "2"
services:
  nacos:
    image: nacos/nacos-server:latest
    container_name: nacos-standalone
    environment:
    - PREFER_HOST_MODE=hostname
    - MODE=standalone
    volumes:
    - ./nacos/standalone-logs/:/home/nacos/logs
    - ./nacos/init.d/custom.properties:/home/nacos/init.d/custom.properties
    ports:
    - "8848:8848"
  sentinel:
    container_name: sentinel
    image: bladex/sentinel-dashboard:latest
    ports:
      - "8787:8858"
    depends_on:
      - nacos
    restart: on-failure
```

执行

```shell
docker-compose -f standalone-derby.yaml up
```

## 9.ftp

```shell
docker pull bogem/ftp
```

```shell
docker run -d -v [docker统一目录]/ftp/vsftpd:/home/vsftpd \
                -p 9020:20 -p 9021:21 -p 47400-47470:47400-47470 \
                -e FTP_USER=pandaz \
                -e FTP_PASS=pandaz \
                -e PASV_ADDRESS=127.0.0.1 \
                --name ftp \
                --restart=always bogem/ftp
```

## 10.Jenkins

```shell
docker pull jenkins/jenkins
```

```shell
docker run -d -p 8800:8080 -p 50001:50001 --env JENKINS_SLAVE_AGENT_PORT=50001 -v /etc/localtime:/etc/localtime -v [docker统一目录]/jenkins/sh/jenkins.sh:/usr/local/bin/jenkins.sh -v [docker统一目录]/jenkins/jenkins_home:/var/jenkins_home --name jenkins --restart=always jenkins/jenkins
```

## 11.Redis Sentinel 集群

compose文件及相关配置都在 [redis-cluster](./conf/redis-cluster) 中

sentinel相关的配置文件，为了方便本地使用，监听了本地修改后的host：carzer.com，可以根据实际情况自行修改IP


## 12.SqlServer

```shell
docker pull mcr.microsoft.com/mssql/server:2019-CU3-ubuntu-18.04

docker run -e "ACCEPT_EULA=Y" -e  "SA_PASSWORD=<YourStrong@Passw0rd>" -p 14330:1433 --name sqlserver2019 -d mcr.microsoft.com/mssql/server:2019-CU3-ubuntu-18.04
```

由于Mac的特殊性，SqlServer在挂载卷的时候会报错：`server error 87(the parameter is incorrect.)`
根据官网的说明：
> Important
>
> Host volume mapping for Docker on Mac with the SQL Server on Linux image is not supported at this time. Use data volume containers instead. This restriction is specific to the /var/opt/mssql directory. Reading from a mounted directory works fine. For example, you can mount a host directory using -v on Mac and restore a backup from a .bak file that resides on the host.

[原文地址](https://docs.microsoft.com/en-us/sql/linux/sql-server-linux-configure-docker?view=sql-server-ver15)

需要使用挂载卷，参照 [docker挂载卷说明](https://docs.docker.com/storage/volumes/)

```shell
docker pull mcr.microsoft.com/mssql/server:2019-CU3-ubuntu-18.04

docker volume create mssql-vol

docker run -e "ACCEPT_EULA=Y" -e  "SA_PASSWORD=Ssa159753." -p 14330:1433 -v mssql-vol:/var/opt/mssql --name sqlserver2019 -d mcr.microsoft.com/mssql/server:2019-CU3-ubuntu-18.04
```

```shell
docker volume inspect mssql-vol
```

>[
     {
         "CreatedAt": "2020-06-19T02:11:32Z",
         "Driver": "local",
         "Labels": {},
         "Mountpoint": "/var/lib/docker/volumes/mssql-vol/_data",
         "Name": "mssql-vol",
         "Options": {},
         "Scope": "local"
     }
 ]  


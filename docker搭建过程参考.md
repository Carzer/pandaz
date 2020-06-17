# 写在前面

> 调整时区:
cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
或
docker cp /etc/localtime [容器ID或者NAME]:/etc/localtime

> docker间互相访问时，可以使用172.17.0.1，而非localhost

>由于 https://hub.docker.com/ 非常容易超时，所以需要改一下docker镜像源  
>找到/etc/docker/daemon.json，添加`{ "registry-mirrors" :["https://docker.mirrors.ustc.edu.cn"]}`    
>如果高版本docker出现问题，可以使用其他方式：`curl -sSL https://get.daocloud.io/daotools/set_mirror.sh | sh -s http://f1361db2.m.daocloud.io`

>个人镜像加速器地址：https://xvaz3vtq.mirror.aliyuncs.com

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
mkdir -p [docker统一目录]/logs/nginx
mkdir -p [docker统一目录]/nginx/html
```

可能用到的配置文件（[docker统一目录]/nginx）  
[nginx.conf](./conf/nginx.conf)  

```shell
mkdir -p [docker统一目录]/nacos/nacos
mkdir -p [docker统一目录]/nacos/nacos/prometheus/prometheus
mkdir -p [docker统一目录]/nacos/sentinel
```

可能用到的配置文件（[docker统一目录]/nacos）  
[standalone-derby.yaml](./conf/standalone-derby.yaml)
[startup.sh](./conf/startup.sh)  
[stop.sh](./conf/stop.sh)  
[start_sentinel.sh](./conf/start_sentinel.sh)

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

mysql高版本会有驱动异常等问题，所以选用5.7.29版本

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
docker run --name nginx -p 8090:8090 -v [docker统一目录]/nginx/nginx.conf:/etc/nginx/nginx.conf -v [docker统一目录]/nginx/ext:/etc/nginx/ext -v [docker统一目录]/logs/nginx:/var/log/nginx -v [docker统一目录]/nginx/html:/etc/nginx/html -d nginx
```

## 8.nacos&sentinel

```shell
git clone --depth 1 https://github.com/nacos-group/nacos-docker.git
cd nacos-docker
```
将example文件夹复制到`[docker统一目录]/nacos/nacos`
修改standalone-derby.yaml文件，增加的内容

1. services.prometheus.volumes

```shell
- ./prometheus/prometheus:/prometheus
```
2. services.nacos.volumes
```shell
- ../sentinel:/sentinel
```
3. services.nacos.ports
```shell
- "8787:8787"
```

执行

~~docker-compose -f example/standalone-derby.yaml up~~

```shell
docker-compose -f standalone-derby.yaml up
```

sentinel在nacos-server的**docker**中运行，执行以下命令，完成sentinel的自启动

```shell
chmod +x startup.sh
chmod +x stop.sh
chmod +x start_sentinel.sh
```

```shell
cp start_sentinel.sh /etc/profile.d/start_sentinel.sh
cp start_sentinel.sh /etc/init.d/start_sentinel.sh
```

```shell
chkconfig --add start_sentinel.sh
chkconfig start_sentinel.sh on
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

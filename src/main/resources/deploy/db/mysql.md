##[mysql document](https://docs.docker.com/samples/library/mysql/)

###download
```
sudo docker pull mysql:5.7
```
### start server instance
```
sudo docker run --name mysql  -v /data/mysql:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=1111 -e MYSQL_USER=root -e MYSQL_DATABASE=qicaiyun   -d  mysql:5.7
```

###connect to MySQL
```
sudo docker run --name mysqlclient  -it --link mysql:mysql --rm mysql:5.7 sh -c 'exec mysql -h"172.17.0.2" -P"3306" -uroot -p"1111"'
```
mysql:mysql  第一个mysql对应的是容器名称
172.17.0.2 是容器IP地址（可以通过命令docker inspect mysql 来获得）

##import data to MySQL
先启动名为mysqlclient的容器链接MySQL服务，然后通过
docker  cp  本地路径 容器ID:/目标路径复制文件到容器
```
docker cp /tmp/jianshu.sql  mysqlclient:/tmp
```
通过mysqlclient容器链接到MySQL服务，通过source 命令导入.sql文件



## 配置文件
配置文件：application.yml  
配置文件（若有）：config/*  
修改后需重启即可生效
## 脚本说明 
### Linux环境 
#### 使用Java 
启动：
~~~
./shell_java_start.sh
~~~
关闭：
~~~
./shell_java_stop.sh
~~~
查看日志
~~~
tail -f -n 100 ./logs/"$(ls -t ./logs | head -n 1)"
~~~

#### 使用Docker
启动：
~~~
docker-compose up -d
~~~
关闭：
~~~
docker-compose down
~~~
查看日志：
~~~
docker logs -f @project.artifactId@
~~~
移除镜像
~~~
docker rmi @project.artifactId@:@project.version@
~~~
### Windows环境
启动：
~~~
双击执行 win_java_start.bat
~~~
## 异常提示操作说明

### 异常
~~~
-bash: ./shell_java_start.sh: /bin/bash^M: bad interpreter: No such file or directory
~~~
则执行如下内容即可
~~~
sed -i "s/\\r//" shell_java_start.sh
~~~

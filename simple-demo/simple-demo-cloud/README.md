1. Nacos远程配置中心  
示例：application.yml  公共-通用配置
~~~
simple:
  openApi:
    version: 1.0.0
    contact:
      name: ok1996
      url: https://ok96.cn
      email: ku29@qq.com
~~~

示例：simple-demo-cloud.yml
~~~
server:
  port: 11100
~~~

2. bootstrap.yml本地工程配置  
输入/修改Nacos连接信息
~~~
-Dspring.cloud.nacos.server-addr=
-Dspring.cloud.nacos.username=
-Dspring.cloud.nacos.password=
-Dspring.cloud.nacos.discovery.namespace=
~~~
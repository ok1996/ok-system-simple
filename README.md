## 核心依赖
| 依赖                   | 版本                  |
|----------------------|---------------------|
| Java                 | 17                  |
| Spring Boot          | 3.0.3               |
| Spring Cloud         | 2022.0.1            |
| Spring Cloud Alibaba | 2022.0.0.0-RC1      |
| Ok System Simple     | 2023.2.3.0-SNAPSHOT |

## Starter自动装配模块
| 模块名称                      | 使用场景     | 说明                                                           |
|---------------------------|----------|--------------------------------------------------------------|
| simple-starter-web        | Web应用    | SpringBoot Web、集成接口文档、序列化格式转换、<br/>响应信息主体、全局异常处理器、Jackson工具类 |
| simple-starter-redisson   | 锁        | 自定义注解简化使用：分布式锁、分布式幂等                                         |
| simple-starter-s3         | 文件存储管理   | 简化导入配置参数，封装管理接口                                              |
| simple-starter-socket     | 长链接通讯及集群 | 集群使用redis订阅及广播机制，无redis自动单机                                  |
| simple-starter-grpc       | 远程调用协议接口 | 自定义注解Grpc简化使用：客户端、服务端                                        |
| simple-starter-datasource | 数据库连接应用  | 数据库连接使用hikari、自动创建数据库                                        |
| simple-starter-freemarker | 模板引擎应用   | 简化导入配置参数                                                     |
| simple-starter-cloud      | 微服务应用    | 微服务常用依赖集成：服务发现、远程配置中心等                                       |

## Base服务类模块
| 模块名称                  | 使用场景 | 说明                    |
|-----------------------|------|-----------------------|
| simple-base-param     | 基础配置 | 启动自动初始化配置、可选覆盖生成、开放接口 |
| simple-base-generator | 代码生成 | 根据数据库表生成基础项目代码        |


## 仓库地址
例 simple-starter-web  
最新版本号 2023.2.2.1
~~~
    <dependencies>
        <dependency>
            <groupId>cn.iosd</groupId>
            <artifactId>simple-starter-web</artifactId>
            <version>2023.2.2.1</version>
        </dependency>
    </dependencies>
~~~

## Demo示例
| 服务                      | HTTP端口号 | 端口号          | 说明                      |
|-------------------------|---------|--------------|-------------------------|
| simple-demo-boot        | 11010   |              | 自动装配类-空项目启动示例           |
| simple-demo-redisson    | 11020   |              | 自动装配类-锁使用示例             |
| simple-demo-s3          | 11030   |              | 自动装配类-文件管理使用示例          |
| simple-demo-socket-one  | 11040   | SOCKET:12010 | 自动装配类-长连接通讯（单体/集群）1使用示例 |
| simple-demo-socket-two  | 11050   | SOCKET:12020 | 自动装配类-长连接通讯（单体/集群）2使用示例 |
| simple-demo-grpc-server | 11060   | GRPC:12030   | 自动装配类-Grpc服务端启动示例       |
| simple-demo-grpc-client | 11070   |              | 自动装配类-Grpc客户端使用示例       |
| simple-demo-datasource  | 11080   |              | 自动装配类-数据库连接查询使用示例       |
| simple-demo-freemarker  | 11090   |              | 自动装配类-freemarker页面使用示例  |
| simple-demo-cloud       | 11100   |              | 自动装配类-微服务注册中心及配置中心使用示例  |
| demo-base-param         | 13010   |              | 服务类-基础参数使用示例            |
| demo-base-generator     |         |              | 服务类-代码生成使用示例            |

## Simple支持配置项
~~~
simple:
  ## simple-starter-web
  openApi:
    title: 接口文档示例
    description: 接口文档示例描述
    version: 1.0.0
    contact:
      name: ok1996
      url: https://ok96.cn
      email: git@ok96.cn
  handler:
    exception:
      #全局异常处理器 缺省项为true 
      enabled: true
  jackson:
    serialize:
      # 序列化格式转换 缺省项为true 
      enabled: true
  ## simple-starter-redisson
  redisson:
    #分布式锁 缺省项为false
    enabled: true
    type: standalone
    address: 127.0.0.1
    password: password
    database: 0
  ## simple-starter-socket
  socket:
    #长链接通讯及集群 缺省项为false
    enabled: true
    port: 12010
    upgradeTimeout: 1000000
    pingTimeout: 6000000
    pingInterval: 25000
  ## simple-starter-s3
  s3:
    #文件存储管理 缺省项为false
    enabled: true
    accessKey: 3sZWX1PLuCYNqzMw
    secretKey: vuLhQbT9iK1EunaLGETtytMBjJTxoHpw
    endpoint: 127.0.0.1:9000
  ## simple-starter-grpc
  grpc:
    client:
      #Grpc客户端 缺省项为false 
      enabled: true
      channel:
        local-grpc-server:
          address: '127.0.0.1:12030'
    server:
      #Grpc服务端 缺省项为false 
      enabled: true
      port: 12030
  ## simple-starter-datasource
  datasource:
    #自动创建数据库 缺省项为true 
    autoCreateDatabase: true
  ## simple-starter-freemarker
  freemarker:  
    resourceVoList:  
        # resourceHandler：访问的前缀 resourceLocations：真实路径
      - resourceHandler:  /layui/**
        resourceLocations:  classpath:/plugin/layui/
      - resourceHandler:  /Users/**
        resourceLocations:  file:/Users/
  base:
    ## simple-base-param
    param:
      ##开启参数配置服务 缺省项为true 
      enabled: true
~~~

## 启动Demo需修改的配置  - Add VM options
DemoBootApplication
~~~
--add-opens
java.base/java.util=ALL-UNNAMED
~~~

DemoCloudApplication
~~~
-Dspring.cloud.nacos.server-addr=
-Dspring.cloud.nacos.username=nacos
-Dspring.cloud.nacos.password=
-Dspring.cloud.nacos.discovery.namespace=
-Dspring.cloud.nacos.config.namespace=
~~~

DemoDatasourceApplication
~~~
-Dspring.datasource.dynamic.datasource.master.url=
-Dspring.datasource.dynamic.datasource.master.username=root
-Dspring.datasource.dynamic.datasource.master.password=
~~~

DemoRedissonApplication
~~~
-Dsimple.redisson.address=
-Dsimple.redisson.password=
~~~

DemoS3Application
~~~
-Dsimple.s3.accessKey=
-Dsimple.s3.secretKey=
-Dsimple.s3.endpoint=
~~~

DemoSocketOneApplication
~~~
-Dsimple.redisson.address=
-Dsimple.redisson.password=
--add-opens
java.base/java.util=ALL-UNNAMED
~~~

DemoSocketTwoApplication
~~~
-Dsimple.redisson.address=
-Dsimple.redisson.password=
--add-opens
java.base/java.util=ALL-UNNAMED
~~~

BaseParamApplication
~~~
-Dspring.datasource.dynamic.datasource.master.url=
-Dspring.datasource.dynamic.datasource.master.username=root
-Dspring.datasource.dynamic.datasource.master.password=
~~~

BaseGeneratorApplication -修改代码
~~~
MybatisGeneratorVo.setDataBaseUrl()
MybatisGeneratorVo.setDataBaseUserName()
MybatisGeneratorVo.setDataBasePassword()
~~~
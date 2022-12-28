## 核心依赖
| 依赖                   | 版本                  |
|----------------------|---------------------|
| Java                 | 17                  |
| Spring Boot          | 3.0.1               |
| Spring Cloud         | 2022.0.0            |
| Spring Cloud Alibaba | 2022.0.0.0-RC1      |
| Ok System Simple     | 2023.1.0.0-SNAPSHOT |

## 模块
| 模块名称                      | 使用场景     | 说明                          |
|---------------------------|----------|-----------------------------|
| simple-starter-web        | Web应用    | SpringBoot Web、集成接口文档       |
| simple-starter-redisson   | 锁        | 自定义注解简化使用：分布式锁、分布式幂等        |
| simple-starter-s3         | 文件存储管理   | 简化导入配置参数，封装管理接口             |
| simple-starter-socket     | 长链接通讯及集群 | 集群使用redis订阅及广播机制，无redis自动单机 |
| simple-starter-grpc       | 远程调用协议接口 | 自定义注解Grpc简化使用：客户端、服务端       |
| simple-starter-datasource | 数据库连接应用  | 数据库连接使用hikari、自动创建数据库       |
| simple-starter-freemarker | 模板引擎应用   | 简化导入配置参数                    |
| simple-starter-cloud      | 微服务应用    | 微服务常用依赖集成：服务发现、远程配置中心等      |

## 端口分配
| 服务                      | HTTP端口号 | 端口号          |
|-------------------------|---------|--------------|
| simple-demo-boot3       | 11010   |              |
| simple-demo-redisson    | 11020   |              |
| simple-demo-s3          | 11030   |              |
| simple-demo-socket-one  | 11040   | SOCKET:12010 |
| simple-demo-socket-two  | 11050   | SOCKET:12020 |
| simple-demo-grpc-server | 11060   | GRPC:12030   |
| simple-demo-grpc-client | 11070   |              |
| simple-demo-datasource  | 11080   |              |
| simple-demo-freemarker  | 11090   |              |
| simple-demo-cloud       | 11100   |              |

## Simple支持配置项
~~~
simple:
  openApi:
    title: 接口文档示例
    description: 接口文档示例描述
    version: 1.0.0
    contact:
      name: ok1996
      url: https://ok96.cn
      email: git@ok96.cn
  redisson:
    #缺省项为false
    enabled: true
    type: standalone
    address: 127.0.0.1
    password: password
    database: 0
  socket:
    #缺省项为false
    enabled: true
    port: 12010
    upgradeTimeout: 1000000
    pingTimeout: 6000000
    pingInterval: 25000
  s3:
    #缺省项为false
    enabled: true
    accessKey: 3sZWX1PLuCYNqzMw
    secretKey: vuLhQbT9iK1EunaLGETtytMBjJTxoHpw
    endpoint: 127.0.0.1:9000
  grpc:
    client:
      #缺省项为false 
      enabled: true
      channel:
        local-grpc-server:
          address: '127.0.0.1:12030'
    server:
      #缺省项为false 
      enabled: true
      port: 12030
  datasource:
    #缺省项为true 
    autoCreateDatabase: true
  freemarker:  
    resourceVoList:  
        # resourceHandler：访问的前缀 resourceLocations：真实路径
      - resourceHandler:  /layui/**
        resourceLocations:  classpath:/plugin/layui/
      - resourceHandler:  /Users/**
        resourceLocations:  file:/Users/
~~~
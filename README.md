## 核心依赖
| 依赖             | 版本      |
|----------------|---------|
| Java           | 17      |
| Spring Boot    | 3.0.0   |
| Graalvm Native | 0.9.18  |

## 模块
| 模块名称                      | 使用技术                        | 实现说明                          |
|---------------------------|-----------------------------|-------------------------------|
| simple-starter-web        | web、springdoc               | SpringBoot Web、接口文档           |
| simple-starter-redisson   | redisson                    | 自定义注解：分布式锁、分布式幂等              |
| simple-starter-s3         | aws-java-sdk-s3             | 导入配置参数，封装管理接口                 |
| simple-starter-socket     | netty-socketIo、redisson     | 导入配置参数，socket集群使用redis订阅及广播机制 |
| simple-starter-grpc       | grpc                        | 自定义注解：客户端、服务端                 |
| simple-starter-datasource | hikari、dynamic、p6spy、flyway | 数据库连接池、多数据源、动态监控、自动建表、自动创建数据库 |

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
~~~
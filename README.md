## Links
- [文档详情](https://ok96.cn/pages/8e9dc3/)
- [更新项说明](https://ok96.cn/pages/ae03ef/)

## 核心依赖
| 依赖                   | 版本             |
|----------------------|----------------|
| Java                 | 17             |
| Spring Boot          | 3.0.4          |
| Spring Cloud         | 2022.0.1       |
| Spring Cloud Alibaba | 2022.0.0.0-RC1 |
| Ok System Simple     | 2023.2.3.0     |

## 仓库地址
例 simple-starter-web  
最新版本号 2023.2.3.0
~~~
    <dependencies>
        <dependency>
            <groupId>cn.iosd</groupId>
            <artifactId>simple-starter-web</artifactId>
            <version>2023.2.3.0</version>
        </dependency>
    </dependencies>
~~~
## Base服务类模块
| 模块名称                  | 使用场景 | 说明                    |
|-----------------------|------|-----------------------|
| simple-base-param     | 基础配置 | 启动自动初始化配置、可选覆盖生成、开放接口 |
| simple-base-generator | 代码生成 | 根据数据库表生成基础项目代码        |

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
| simple-starter-encode     | 加密       | 注解加密脱敏（服务类返回参数支持单体字段、列表、实体嵌套）                                |





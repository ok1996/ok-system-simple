## 这个项目是做什么的？

基于SpringBoot提供常用的自动装配模块，能快速提供各模块所需的服务

<p>
  <a href="https://mvnrepository.com/search?q=cn.iosd">
    <img alt="maven" src="https://img.shields.io/badge/maven-repository-blue?style=flat-square&logo=apachemaven">
  </a>

  <a href="https://central.sonatype.com/search?q=g%3Acn.iosd+a%3Asimple-starter">
    <img alt="maven" src="https://img.shields.io/maven-central/v/cn.iosd/simple-starter.svg?style=flat-square&logo=apachemaven">
  </a>

  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square&logo=apache">
  </a>

  <a href="https://github.com/ok1996/ok-system-simple/releases">
    <img alt="releases" src="https://img.shields.io/github/release/ok1996/ok-system-simple.svg?style=flat-square&logo=semanticrelease">
  </a>

  <a href="https://app.codacy.com/gh/ok1996/ok-system-simple/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade">
    <img src="https://app.codacy.com/project/badge/Grade/32f59a4b8afd4035a0da527009690541"/>
  </a>
</p>

## 该如何开始？

找到对应所需的starter模块，引入依赖

例，需要快速集成一个拥有Web应用和集成接口文档的服务

引入依赖
~~~
    <dependencies>
        <dependency>
            <groupId>cn.iosd</groupId>
            <artifactId>simple-starter-web</artifactId>
        </dependency>
    </dependencies>
~~~

## 核心依赖

| 依赖                   | 版本                  |
|----------------------|---------------------|
| Java                 | 17                  |
| Spring Boot          | 3.2.0               |
| Spring Cloud         | 2023.0.0            |
| Spring Cloud Alibaba | 2022.0.0.0          |
| Ok System Simple     | 2024.1.0.0-SNAPSHOT |

## Starter自动装配模块

| 模块名称                      | 使用场景     | 说明                                                           |
|---------------------------|----------|--------------------------------------------------------------|
| simple-starter-web        | Web应用    | SpringBoot Web、集成接口文档、序列化格式转换、<br/>响应信息主体、全局异常处理器、Jackson工具类 |
| simple-starter-redisson   | 锁        | 自定义注解简化使用：分布式锁、分布式幂等; <br/> Cacheable注解常用配置及自定义CacheName过期时间 |
| simple-starter-s3         | 文件管理客户端  | 初始化客户端、提供基础服务方法                                              |
| simple-starter-socket     | 长链接通讯及集群 | 集群使用redis订阅及广播机制，无redis自动单机                                  |
| simple-starter-grpc       | 远程调用协议接口 | 自定义注解Grpc简化使用：客户端、服务端                                        |
| simple-starter-datasource | 数据库连接应用  | HikariCP、P6spy、自动创建数据库表、<br/>通用CURD控制层、自定义JsonNode处理器        |
| simple-starter-freemarker | 模板引擎应用   | 简化导入配置参数                                                     |
| simple-starter-cloud      | 微服务应用    | 微服务常用依赖集成：服务发现、远程配置中心等                                       |
| simple-starter-gateway    | 微服务网关    | 微服务接口网关：集成微服务文档网关                                            |
| simple-starter-encrypt    | 加密       | 注解：加密脱敏（加密规则有通用模板、或自定义）<br/>注解：接口请求参数或返回参数Rsa加密解密            |
| simple-starter-dict       | 字典       | 注解字典翻译（字典支持文件json、调用接口或自定义实现）<br/>（服务类返回参数支持单体字段、列表、实体嵌套）    |
| simple-starter-email      | 邮件       | Smtp邮件推送                                                     |
| simple-starter-package    | 构建打包     | 自定义Maven构建打包逻辑，将常用脚本及可执行文件统一打包                               |

## Base服务类模块

| 模块名称                  | 使用场景     | 说明                    |
|-----------------------|----------|-----------------------|
| simple-base-param     | 基础配置     | 启动自动初始化配置、可选覆盖生成、开放接口 |
| simple-base-generator | 代码生成     | 根据数据库表生成基础项目代码        |
| simple-base-s3        | 文件存储管理服务 | 提供文件管理服务及接口           |
| simple-base-dict      | 字典管理服务   | 字典管理服务及接口             |

## Utils工具类模块

| 模块名称                 | 使用场景   | 说明                     |
|----------------------|--------|------------------------|
| simple-utils-jackson | Json工具 | 提供 Json 序列化和反序列化功能的工具类 |
| simple-utils-common  | 通用工具   | 提供常用及通用的工具类            |

## 鸣谢

感谢 JetBrains [OpenSourceSupport](https://jb.gg/OpenSourceSupport) 所提供的支持

<div>
<img src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.svg" width="200" height="200"/>

<img src="https://resources.jetbrains.com/storage/products/company/brand/logos/IntelliJ_IDEA_icon.svg" width="175" height="175"/>
</div>


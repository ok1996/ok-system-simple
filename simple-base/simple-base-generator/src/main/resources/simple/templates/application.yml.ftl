## 可共享配置
spring:
  datasource:
    dynamic:
      p6spy: true
      primary: master
      hikari:
        max-pool-size: 20
        min-idle: 5
        is-auto-commit: true
        idle-timeout: 600000
        connection-timeout: 10000
        max-lifetime: 900000
        connection-test-query: SELECT 1
      datasource:
        # 主库数据源
        master:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://127.0.0.1:3306/simple_demo?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
          username: root
          password: password

  flyway:
    #  当迁移时发现目标schema非空，而且带有没有元数据的表时，是否自动执行基准迁移，默认false.
    baseline-on-migrate: true
    locations: classpath:db/mysql
    table: ${"${"}spring.application.name}_flyway_schema_history
# mybatis配置
mybatis-plus:
  # 配置mapper的扫描，找到所有的mapper.xml映射文件
  mapperLocations: classpath*:${mapperLocations}**/*Mapper.xml
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
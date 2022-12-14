package cn.iosd.demo.datasource.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@MapperScan("cn.iosd.demo.datasource.mapper")
public class DemoDatasourceAutoConfiguration {
}

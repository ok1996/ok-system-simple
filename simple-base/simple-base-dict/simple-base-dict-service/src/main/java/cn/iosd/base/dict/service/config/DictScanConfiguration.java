package cn.iosd.base.dict.service.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置ComponentScan以及是否启用
 *
 * @author ok1996
 */
@Configuration
@ComponentScan(value = {"cn.iosd.base.dict.service"})
@ConditionalOnProperty(value = "simple.scan.service.enabled", havingValue = "true", matchIfMissing = true)
public class DictScanConfiguration {

}

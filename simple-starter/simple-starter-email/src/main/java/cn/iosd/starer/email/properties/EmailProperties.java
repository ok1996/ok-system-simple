package cn.iosd.starer.email.properties;

import cn.iosd.starer.email.vo.EmailConfigVo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@ConfigurationProperties("simple.email")
public class EmailProperties {

    private EmailConfigVo config;

    public EmailConfigVo getConfig() {
        return config;
    }

    public void setConfig(EmailConfigVo config) {
        this.config = config;
    }
}

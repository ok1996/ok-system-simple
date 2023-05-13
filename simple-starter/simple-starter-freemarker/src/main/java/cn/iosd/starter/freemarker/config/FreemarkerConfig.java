package cn.iosd.starter.freemarker.config;

import freemarker.template.Configuration;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author ok1996
 */
@Component
public class FreemarkerConfig {

    @Autowired
    private Configuration configuration;

    @PostConstruct
    public void setSharedVariable() {
        configuration.setNumberFormat("#");
    }

}
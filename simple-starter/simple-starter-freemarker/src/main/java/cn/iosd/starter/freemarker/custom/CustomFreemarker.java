package cn.iosd.starter.freemarker.custom;

import cn.iosd.starter.freemarker.config.MyFreemarkerView;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * 增加自定义视图变量和方法
 *
 * @author ok1996
 */
public class CustomFreemarker {

    @Bean
    public CommandLineRunner customFreemarker(FreeMarkerViewResolver resolver) {
        return strings -> resolver.setViewClass(MyFreemarkerView.class);
    }
}

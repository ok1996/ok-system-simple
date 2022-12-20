package cn.iosd.starter.freemarker.properties;

import cn.iosd.starter.freemarker.vo.ResourceVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * @author ok1996
 */
@Data
@Configuration
@ConfigurationProperties("simple.freemarker")
public class FreemarkerProperties {

    private List<ResourceVo> resourceVoList;

}

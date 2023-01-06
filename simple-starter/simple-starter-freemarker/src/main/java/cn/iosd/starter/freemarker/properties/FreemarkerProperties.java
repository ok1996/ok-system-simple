package cn.iosd.starter.freemarker.properties;

import cn.iosd.starter.freemarker.vo.ResourceVo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * @author ok1996
 */
@Configuration
@ConfigurationProperties("simple.freemarker")
public class FreemarkerProperties {

    private List<ResourceVo> resourceVoList;

    public List<ResourceVo> getResourceVoList() {
        return resourceVoList;
    }

    public void setResourceVoList(List<ResourceVo> resourceVoList) {
        this.resourceVoList = resourceVoList;
    }
}

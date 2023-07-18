package cn.iosd.starter.datasource.mybatis;

import java.util.List;

/**
 * 定义 MybatisPlus mapper-locations 配置
 *
 * @author ok1996
 */
public class MapperLocations {
    private List<String> locations;

    public MapperLocations() {
    }

    public MapperLocations(List<String> locations) {
        this.locations = locations;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}

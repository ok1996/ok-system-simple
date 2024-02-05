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

    public MapperLocations(String location) {
        this.locations = List.of(location);
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    /**
     * 简洁方式 创建 MapperLocations 对象
     *
     * @param location 资源路径
     * @return MapperLocations 对象
     */
    public static MapperLocations of(String location) {
        return new MapperLocations(location);
    }

    /**
     * 简洁方式 创建 MapperLocations 对象
     *
     * @param locations 资源路径
     * @return MapperLocations 对象
     */
    public static MapperLocations of(List<String> locations) {
        return new MapperLocations(locations);
    }
}

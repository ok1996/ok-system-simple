package cn.iosd.starter.datasource.mybatis.provider;

import java.util.List;

/**
 * 定义获取 MybatisPlus mapper-locations 配置的接口
 *
 * @author ok1996
 */
public interface MapperLocationsProvider {

    /**
     * 获取 mapper-locations 的配置值
     *
     * @return 返回 mapper-locations 的配置值，可能为列表
     */
    List<String> getMapperLocations();

}

package cn.iosd.starter.freemarker.vo;

import lombok.Data;


/**
 * @author ok1996
 */
@Data
public class ResourceVo {
    /**
     * 访问前缀
     */
    private String resourceHandler;
    /**
     * 文件地址访问真实路径
     */
    private String resourceLocations;
}

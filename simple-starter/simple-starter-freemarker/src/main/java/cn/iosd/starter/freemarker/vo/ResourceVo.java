package cn.iosd.starter.freemarker.vo;

/**
 * @author ok1996
 */
public class ResourceVo {
    /**
     * 访问前缀
     */
    private String resourceHandler;
    /**
     * 文件地址访问真实路径
     */
    private String resourceLocations;

    public String getResourceHandler() {
        return resourceHandler;
    }

    public void setResourceHandler(String resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    public String getResourceLocations() {
        return resourceLocations;
    }

    public void setResourceLocations(String resourceLocations) {
        this.resourceLocations = resourceLocations;
    }
}

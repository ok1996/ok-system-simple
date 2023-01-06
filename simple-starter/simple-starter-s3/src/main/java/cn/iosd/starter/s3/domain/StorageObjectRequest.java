package cn.iosd.starter.s3.domain;


/**
 * 存储对象列表请求参数
 *
 * @author ok1996
 */
public class StorageObjectRequest {
    /**
     * 存储桶
     */
    private String bucketName;
    /**
     * 文件名前缀
     */
    private String prefixFileName;
    /**
     * 每页大小
     */
    private Integer pageSize;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getPrefixFileName() {
        return prefixFileName;
    }

    public void setPrefixFileName(String prefixFileName) {
        this.prefixFileName = prefixFileName;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

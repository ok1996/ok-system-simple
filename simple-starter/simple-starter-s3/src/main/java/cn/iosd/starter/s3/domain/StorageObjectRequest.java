package cn.iosd.starter.s3.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 存储对象列表请求参数
 *
 * @author ok1996
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

}

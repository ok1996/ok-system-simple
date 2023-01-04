package cn.iosd.starter.s3.service;

import cn.iosd.starter.s3.domain.StorageObjectRequest;
import cn.iosd.starter.s3.domain.StorageObjectResponse;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;

import java.io.InputStream;
import java.util.List;

/**
 * @author ok1996
 */
public interface SimpleStorageService {
    /**
     * 上传文件
     *
     * @param contentType 内容类型
     * @param size        内容大小
     * @param input       内容流
     * @param bucket      存储桶
     * @param key         文件主键（eg:awed13.jpg）
     * @return
     */
    String upload(String contentType, Long size, InputStream input, String bucket, String key);

    /**
     * 获取桶列表
     *
     * @param bucketName 桶名称 非必填 关键字过滤
     * @return
     */
    List<Bucket> getListBuckets(String bucketName);

    /**
     * 创建桶
     *
     * @param bucketName 桶名称
     * @return
     */
    Bucket creatBucket(String bucketName);

    /**
     * 删除桶
     *
     * @param bucketName 桶名称
     */
    void deleteBucket(String bucketName);

    /**
     * 获取对象-首页列表
     *
     * @param storageObjectRequest
     * @return
     */
    StorageObjectResponse getStorageObject(StorageObjectRequest storageObjectRequest);

    /**
     * 获取对象-下一页列表
     *
     * @param objectListing 首页信息
     * @return
     */
    StorageObjectResponse getStorageObjectNext(ObjectListing objectListing);

    /**
     * 删除对象
     *
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @return
     */
    Boolean deleteStorageObject(String bucketName, String objectName);
}

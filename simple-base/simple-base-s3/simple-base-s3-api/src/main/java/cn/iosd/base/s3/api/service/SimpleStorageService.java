package cn.iosd.base.s3.api.service;

import cn.iosd.base.s3.api.domain.StorageObjectRequest;
import cn.iosd.base.s3.api.domain.StorageObjectResponse;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author ok1996
 */
public interface SimpleStorageService {

    /**
     * 上传文件
     *
     * @param file          文件
     * @param bucket        存储桶
     * @param fileExtension 文件后缀 （eg:jpg）
     * @return 文件主键 （eg:awed13.jpg）
     * @throws IOException 通过文件获取流异常抛出
     */
    String uploadMultipartFile(MultipartFile file, String bucket, String fileExtension) throws IOException;

    /**
     * 生成带有预签名的URL，用于私有S3对象的访问
     *
     * @param bucket 存储桶的名称
     * @param key    存储对象的键
     * @return 带有预签名的URL
     */
    String generatePresignedUrl(String bucket, String key);

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
     * @param key        文件主键（eg:awed13.jpg）
     * @return
     */
    void deleteStorageObject(String bucketName, String key);
}

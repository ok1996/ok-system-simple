package cn.iosd.base.s3.api.feign;

import cn.iosd.base.s3.api.domain.StorageObjectRequest;
import cn.iosd.base.s3.api.domain.StorageObjectResponse;
import cn.iosd.starter.web.domain.Response;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * @author ok1996
 */
@FeignClient(name = "simple-base-s3-service", contextId = "baseS3Feign", path = "/simple-base-s3-service/storage"
        , url = "${simple.feign.base.s3.url:}", primary = false)
public interface SimpleStorageFeign {

    /**
     * 获取存储桶列表
     *
     * @param bucketName 存储桶
     * @return 存储桶列表
     */
    @GetMapping("/bucket/list")
    Response<List<Bucket>> getListBuckets(@RequestParam("bucketName") String bucketName);

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶
     * @return 存储桶信息
     */
    @GetMapping("/bucket")
    Response<Bucket> creatBucket(@RequestParam("bucketName") String bucketName);

    /**
     * 删除存储桶
     *
     * @param bucketName 存储桶
     * @return 响应状态
     */
    @DeleteMapping("/bucket")
    Response<?> deleteBucket(@RequestParam("bucketName") String bucketName);

    /**
     * 获取文件列表-首页
     *
     * @param storageObjectRequest 存储对象列表请求参数
     * @return 文件列表-首页
     */
    @PostMapping("/object")
    Response<StorageObjectResponse> getStorageObject(@RequestBody StorageObjectRequest storageObjectRequest);

    /**
     * 获取文件列表-下一页
     *
     * @param objectListing 本页的对象列表信息
     * @return 文件列表-下一页
     */
    @PostMapping("/object/Next")
    Response<StorageObjectResponse> getStorageObjectNext(@RequestBody ObjectListing objectListing);

    /**
     * 上传文件-返回文件key
     *
     * @param file       文件流
     * @param bucketName 存储桶
     * @return 文件key
     */
    @PostMapping(value = "/object/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    Response<String> uploadMultipartFile(@RequestPart MultipartFile file, @RequestParam("bucketName") String bucketName);

    /**
     * 生成带有预签名的URL，用于私有S3对象的访问
     *
     * @param bucketName 存储桶
     * @param key        文件主键
     * @return 带有预签名的URL
     */
    @GetMapping(value = "/object/url")
    Response<String> generatePresignedUrl(@RequestParam("bucketName") String bucketName, @RequestParam("key") String key);

    /**
     * 删除文件
     *
     * @param bucketName 存储桶
     * @param key        文件主键
     * @return 响应状态
     */
    @DeleteMapping("/object")
    Response<?> deleteStorageObject(@RequestParam("bucketName") String bucketName, @RequestParam("key") String key);
}

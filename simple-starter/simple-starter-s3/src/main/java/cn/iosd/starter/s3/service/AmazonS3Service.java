package cn.iosd.starter.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ok1996
 */
@Service
public class AmazonS3Service{
    @Autowired
	@Qualifier(value="amazonS3Simple")
    private AmazonS3 client;

    /**
     * 上传对象到指定的S3桶中
     *
     * @param metadata 存储对象的元数据
     * @param input    存储对象的数据流
     * @param bucket   存储对象的S3桶名
     * @param key      存储对象的键
     */
    public void putObject(ObjectMetadata metadata, InputStream input, String bucket, String key) {
        client.putObject(new PutObjectRequest(bucket, key, input, metadata));
    }

    /**
     * 上传公共读取的对象到指定的S3桶中
     *
     * @param metadata 存储对象的元数据
     * @param input    存储对象的数据流
     * @param bucket   存储对象的S3桶名
     * @param key      存储对象的键
     */
    public void putObjectPublicRead(ObjectMetadata metadata, InputStream input, String bucket, String key) {
        client.putObject(new PutObjectRequest(bucket, key, input, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    /**
     * 生成带有预签名的URL，用于私有S3对象的访问
     *
     * @param bucket 存储桶的名称
     * @param key    存储对象的键
     * @return 带有预签名的URL
     */
    public URL generatePresignedUrl(String bucket, String key) {
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucket, key);
        return client.generatePresignedUrl(urlRequest);
    }

    /**
     * 获取所有S3存储桶或按名称过滤存储桶列表
     *
     * @param bucketNameFilter 存储桶名称的过滤条件，如果为null或空字符串则不过滤
     * @return 存储桶列表，如果存在过滤条件则返回符合条件的存储桶列表
     */
    public List<Bucket> getListBuckets(String bucketNameFilter) {
        List<Bucket> buckets = client.listBuckets();
        if (StringUtils.isBlank(bucketNameFilter)) {
            return buckets;
        }
        return buckets.stream()
                .filter(bucket -> bucket.getName().contains(bucketNameFilter))
                .collect(Collectors.toList());
    }

    public Bucket createBucket(String bucketName) {
        return client.createBucket(bucketName);
    }

    public void deleteBucket(String bucketName) {
        client.deleteBucket(bucketName);
    }

    /**
     * 列举指定Bucket下的对象
     *
     * @param bucketName     目标Bucket名称
     * @param prefixFileName 指定前缀文件名，用于筛选需要列举的对象
     * @param pageSize       最大返回数量
     * @return 对象列表
     */
    public ObjectListing listObjects(String bucketName, String prefixFileName, Integer pageSize) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix(prefixFileName)
                .withMaxKeys(pageSize);
        return client.listObjects(listObjectsRequest);
    }

    /**
     * 获取下一批对象的列表信息
     *
     * @param objectListing 当前对象列表信息
     * @return 下一批对象的列表信息
     */
    public ObjectListing listNextBatchOfObjects(ObjectListing objectListing) {
        return client.listNextBatchOfObjects(objectListing);
    }

    /**
     * 从指定的 Amazon S3 存储桶中删除一个对象。
     *
     * @param bucketName 包含要删除的对象的桶的名称
     * @param key        要删除的对象所在的key
     */
    public void deleteObject(String bucketName, String key) {
        DeleteObjectRequest dor = new DeleteObjectRequest(bucketName, key);
        client.deleteObject(dor);
    }
}

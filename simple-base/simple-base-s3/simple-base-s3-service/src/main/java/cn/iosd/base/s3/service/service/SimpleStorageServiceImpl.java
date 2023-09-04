package cn.iosd.base.s3.service.service;

import cn.iosd.base.s3.api.domain.StorageObjectRequest;
import cn.iosd.base.s3.api.domain.StorageObjectResponse;
import cn.iosd.base.s3.api.service.SimpleStorageService;
import cn.iosd.starter.s3.service.AmazonS3Service;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author ok1996
 */
@Primary
@Service
public class SimpleStorageServiceImpl implements SimpleStorageService {
    @Autowired
    private AmazonS3Service amazonS3Service;

    @Override
    public String uploadMultipartFile(MultipartFile file, String bucket, String fileExtension) throws IOException {
        String fileKey = RandomStringUtils.randomAlphabetic(12) + "." + fileExtension;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Service.putObject(metadata, file.getInputStream(), bucket, fileKey);
        return fileKey;
    }

    @Override
    public String generatePresignedUrl(String bucket, String key) {
        return amazonS3Service.generatePresignedUrl(bucket, key).toString();
    }

    @Override
    public List<Bucket> getListBuckets(String bucketName) {
        return amazonS3Service.getListBuckets(bucketName);
    }

    @Override
    public Bucket creatBucket(String bucketName) {
        return amazonS3Service.createBucket(bucketName);
    }

    @Override
    public void deleteBucket(String bucketName) {
        amazonS3Service.deleteBucket(bucketName);
    }

    @Override
    public StorageObjectResponse getStorageObject(StorageObjectRequest sor) {
        return convertToSor(amazonS3Service.listObjects(sor.getBucketName()
                , sor.getPrefixFileName()
                , sor.getPageSize()));
    }

    @Override
    public StorageObjectResponse getStorageObjectNext(ObjectListing objectListing) {
        return convertToSor(amazonS3Service.listNextBatchOfObjects(objectListing));
    }

    /**
     * 将ObjectListing对象转换成StorageObjectResponse对象
     *
     * @param objectListing 待转换的ObjectListing对象
     * @return 转换后的StorageObjectResponse对象
     */
    public StorageObjectResponse convertToSor(ObjectListing objectListing) {
        StorageObjectResponse response = new StorageObjectResponse();
        response.setSummaries(objectListing.getObjectSummaries());
        response.setObjectListing(objectListing);
        return response;
    }

    @Override
    public void deleteStorageObject(String bucketName, String key) {
        amazonS3Service.deleteObject(bucketName, key);
    }

}

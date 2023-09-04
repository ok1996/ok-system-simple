package cn.iosd.base.s3.api.service.impl;

import cn.iosd.base.s3.api.domain.StorageObjectRequest;
import cn.iosd.base.s3.api.domain.StorageObjectResponse;
import cn.iosd.base.s3.api.feign.SimpleStorageFeign;
import cn.iosd.base.s3.api.service.SimpleStorageService;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author ok1996
 */
@Service
public class SimpleStorageServiceFeignImpl implements SimpleStorageService {

    @Autowired
    private SimpleStorageFeign simpleStorageFeign;

    @Override
    public String uploadMultipartFile(MultipartFile file, String bucket, String fileExtension) throws IOException {
        return simpleStorageFeign.uploadMultipartFile(file,bucket,fileExtension).dataOrThrowExceptionIfNotSuccess();
    }

    @Override
    public String generatePresignedUrl(String bucket, String key) {
        return simpleStorageFeign.generatePresignedUrl(bucket, key).dataOrThrowExceptionIfNotSuccess();
    }

    @Override
    public List<Bucket> getListBuckets(String bucketName) {
        return simpleStorageFeign.getListBuckets(bucketName).dataOrThrowExceptionIfNotSuccess();
    }

    @Override
    public Bucket creatBucket(String bucketName) {
        return simpleStorageFeign.creatBucket(bucketName).dataOrThrowExceptionIfNotSuccess();
    }

    @Override
    public void deleteBucket(String bucketName) {
        simpleStorageFeign.deleteBucket(bucketName).throwExceptionIfNotSuccess();
    }

    @Override
    public StorageObjectResponse getStorageObject(StorageObjectRequest storageObjectRequest) {
        return simpleStorageFeign.getStorageObject(storageObjectRequest).dataOrThrowExceptionIfNotSuccess();
    }

    @Override
    public StorageObjectResponse getStorageObjectNext(ObjectListing objectListing) {
        return simpleStorageFeign.getStorageObjectNext(objectListing).dataOrThrowExceptionIfNotSuccess();
    }

    @Override
    public void deleteStorageObject(String bucketName, String key) {
        simpleStorageFeign.deleteStorageObject(bucketName, key).throwExceptionIfNotSuccess();
    }
}

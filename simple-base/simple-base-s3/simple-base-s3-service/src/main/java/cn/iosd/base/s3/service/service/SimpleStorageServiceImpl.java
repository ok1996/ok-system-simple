package cn.iosd.base.s3.service.service;

import cn.iosd.base.s3.api.domain.StorageObjectRequest;
import cn.iosd.base.s3.api.domain.StorageObjectResponse;
import cn.iosd.base.s3.api.service.SimpleStorageService;
import cn.iosd.starter.s3.service.AmazonS3Service;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

/**
 * @author ok1996
 */
@Primary
@Service
public class SimpleStorageServiceImpl implements SimpleStorageService {
    @Autowired
    private AmazonS3Service amazonS3Service;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Override
    public String uploadMultipartFile(MultipartFile file, String bucket) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("MultipartFile is null or empty");
        }

        String fileKey = generateRandomString(19);
        String originalFilename = file.getOriginalFilename();
        String comma = ".";
        if (originalFilename != null && originalFilename.contains(comma)) {
            fileKey = fileKey + comma
                    + FilenameUtils.getExtension(file.getOriginalFilename());
        }
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        try {
            amazonS3Service.putObject(metadata, file.getInputStream(), bucket, fileKey);
        } catch (IOException e) {
            throw new IllegalArgumentException("Exception in case of access errors", e);
        }
        return fileKey;
    }

    @Override
    public String generatePresignedUrl(String bucket, String key) {
        return amazonS3Service.generatePresignedUrl(bucket, key, 15L).toString();
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
        return new StorageObjectResponse(amazonS3Service.listObjects(sor.getBucketName()
                , sor.getPrefixFileName()
                , sor.getPageSize()));
    }

    @Override
    public StorageObjectResponse getStorageObjectNext(ObjectListing objectListing) {
        return new StorageObjectResponse(amazonS3Service.listNextBatchOfObjects(objectListing));
    }

    @Override
    public void deleteStorageObject(String bucketName, String key) {
        amazonS3Service.deleteObject(bucketName, key);
    }


    public static String generateRandomString(int length) {
        return RandomStringUtils.random(length, 0, 0, true, true, null, SECURE_RANDOM);
    }
}

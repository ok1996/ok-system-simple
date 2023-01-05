package cn.iosd.starter.s3.service.impl;

import cn.iosd.starter.s3.domain.StorageObjectRequest;
import cn.iosd.starter.s3.domain.StorageObjectResponse;
import cn.iosd.starter.s3.properties.S3Properties;
import cn.iosd.starter.s3.service.SimpleStorageService;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
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
public class SimpleStorageServiceImpl implements SimpleStorageService, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(SimpleStorageServiceImpl.class);

    @Autowired
    private S3Properties s3Properties;

    private AmazonS3 client;

    @Override
    public void afterPropertiesSet() {
        ClientConfiguration config = new ClientConfiguration();
        config.setProtocol(Protocol.HTTP);
        config.disableSocketProxy();

        this.client = AmazonS3ClientBuilder
                .standard()
                .withClientConfiguration(config)
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey())))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(s3Properties.getEndpoint(), Regions.CN_NORTH_1.getName()))
                .enablePathStyleAccess()
                .build();
        log.info("AmazonS3完成配置");
    }

    @Override
    public String upload(String contentType, Long size, InputStream input, String bucket, String key) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(size);
        client.putObject(new PutObjectRequest(bucket, key, input, metadata).withCannedAcl(CannedAccessControlList.PublicRead));
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucket, key);
        URL url = client.generatePresignedUrl(urlRequest);
        return s3Properties.getEndpoint() + url.getPath();
    }

    @Override
    public List<Bucket> getListBuckets(String bucketName) {
        List<Bucket> buckets = client.listBuckets();
        if (StringUtils.isNotBlank(bucketName)) {
            buckets = buckets.stream().filter(s -> s.getName().contains(bucketName)).collect(Collectors.toList());
        }
        return buckets;
    }

    @Override
    public Bucket creatBucket(String bucketName) {
        return client.createBucket(bucketName);
    }

    @Override
    public void deleteBucket(String bucketName) {
        client.deleteBucket(bucketName);
    }

    @Override
    public StorageObjectResponse getStorageObject(StorageObjectRequest storageObjectRequest) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(storageObjectRequest.getBucketName())
                .withPrefix(storageObjectRequest.getPrefixFileName())
                .withMaxKeys(storageObjectRequest.getPageSize());
        ObjectListing objectListing = client.listObjects(listObjectsRequest);
        List<S3ObjectSummary> summaries = objectListing.getObjectSummaries();
        StorageObjectResponse response = new StorageObjectResponse();
        response.setSummaries(summaries);
        response.setObjectListing(objectListing);
        return response;
    }

    @Override
    public StorageObjectResponse getStorageObjectNext(ObjectListing objectListing) {
        ObjectListing objectListingNext = client.listNextBatchOfObjects(objectListing);
        List<S3ObjectSummary> summaries = objectListing.getObjectSummaries();
        StorageObjectResponse response = new StorageObjectResponse();
        response.setSummaries(summaries);
        response.setObjectListing(objectListingNext);
        return response;
    }

    @Override
    public Boolean deleteStorageObject(String bucketName, String objectName) {
        DeleteObjectsRequest dor = new DeleteObjectsRequest(bucketName)
                .withKeys(objectName);
        DeleteObjectsResult deleteObjectsResult = client.deleteObjects(dor);
        try {
            return !deleteObjectsResult.getDeletedObjects().get(0).isDeleteMarker();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}

package cn.iosd.demo.s3.service;

import cn.iosd.starter.s3.service.AmazonS3Service;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author ok1996
 */
@Service
public class SimpleStorageServiceImpl {
    @Autowired
    private AmazonS3Service amazonS3Service;

    public void upload(String contentType, Long size, InputStream input, String bucket, String key) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(size);
        amazonS3Service.putObject(metadata, input, bucket, key);
    }

}

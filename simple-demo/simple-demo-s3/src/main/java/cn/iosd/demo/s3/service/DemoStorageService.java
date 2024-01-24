package cn.iosd.demo.s3.service;

import cn.iosd.starter.s3.service.AmazonS3Service;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ok1996
 */
@Service
public class DemoStorageService {
    @Autowired
    private AmazonS3Service amazonS3Service;

    public List<Bucket> getListBuckets() {
        return amazonS3Service.getListBuckets(null);
    }

}

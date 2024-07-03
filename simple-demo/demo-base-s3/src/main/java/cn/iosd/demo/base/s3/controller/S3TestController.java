package cn.iosd.demo.base.s3.controller;

import cn.iosd.base.s3.api.service.SimpleStorageService;
import cn.iosd.starter.web.domain.Response;
import com.amazonaws.services.s3.model.Bucket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ok1996
 */
@Tag(name = "测试模块")
@RestController
@RequestMapping("/demo-base-s3/test")
public class S3TestController {

    @Autowired(required = false)
    private SimpleStorageService simpleStorageService;

    @Operation(summary = "上传文件")
    @PostMapping(value = "/upload")
    public Response<String> uploadMultipartFile(@ModelAttribute MultipartFile file
            , @Parameter(description = "存储桶") String bucketName) {
        return Response.ok(simpleStorageService.uploadMultipartFile(file, bucketName));
    }

    @Operation(summary = "获取存储桶列表")
    @GetMapping("/bucket/list")
    public Response<List<Bucket>> getListBuckets(@Parameter(description = "存储桶") String bucketName) {
        return Response.ok(simpleStorageService.getListBuckets(bucketName));
    }

}

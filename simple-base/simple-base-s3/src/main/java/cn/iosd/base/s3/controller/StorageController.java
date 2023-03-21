package cn.iosd.base.s3.controller;

import cn.iosd.base.s3.domain.StorageObjectRequest;
import cn.iosd.base.s3.domain.StorageObjectResponse;
import cn.iosd.base.s3.service.SimpleStorageService;
import cn.iosd.starter.web.domain.Response;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author ok1996
 */
@Tag(name = "存储服务")
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired(required = false)
    private SimpleStorageService simpleStorageService;

    @Operation(summary = "获取存储桶列表")
    @GetMapping("/list")
    public Response<List<Bucket>> getListBuckets(@Parameter(description = "存储桶") String bucketName) {
        return Response.ok(simpleStorageService.getListBuckets(bucketName));
    }

    @Operation(summary = "创建存储桶")
    @GetMapping("/bucket")
    public Response<Bucket> creatBucket(@Parameter(description = "存储桶") String bucketName) {
        return Response.ok(simpleStorageService.creatBucket(bucketName));
    }

    @Operation(summary = "删除存储桶")
    @DeleteMapping("/bucket")
    public Response deleteBucket(@Parameter(description = "存储桶") String bucketName) {
        simpleStorageService.deleteBucket(bucketName);
        return Response.ok();
    }

    @Operation(summary = "获取文件列表-首页")
    @PostMapping("/object")
    public Response<StorageObjectResponse> getStorageObject(@RequestBody StorageObjectRequest storageObjectRequest) {
        return Response.ok(simpleStorageService.getStorageObject(storageObjectRequest));
    }

    @Operation(summary = "获取文件列表-下一页")
    @PostMapping("/object/Next")
    public Response<StorageObjectResponse> getStorageObjectNext(
            @RequestBody @Parameter(name = "本页对象列表信息") ObjectListing objectListing) {
        return Response.ok(simpleStorageService.getStorageObjectNext(objectListing));
    }

    @Operation(summary = "上传文件-返回文件key")
    @PostMapping(value = "/object/upload")
    public Response<String> upload(@ModelAttribute MultipartFile file
            , @Parameter(description = "存储桶") @RequestParam(value = "bucketName") String bucketName
            , @Parameter(description = "文件名后缀", example = "png") @RequestParam(value = "fileExtension") String fileExtension) throws IOException {
        String fileKey = RandomStringUtils.randomAlphabetic(12) + "." + fileExtension;
        simpleStorageService.upload(file.getContentType(), file.getSize(),
                file.getInputStream(), bucketName, fileKey);
        return Response.ok(fileKey);
    }

    @Operation(summary = "生成带有预签名的URL，用于私有S3对象的访问")
    @GetMapping(value = "/object/url")
    public Response<String> fd(@Parameter(description = "存储桶") String bucketName, @Parameter(description = "文件主键") String key) {
        return Response.ok(simpleStorageService.generatePresignedUrl(bucketName, key));
    }

    @Operation(summary = "删除文件")
    @DeleteMapping("/object")
    public Response deleteStorageObject(@Parameter(description = "存储桶") String bucketName, @Parameter(description = "文件主键") String key) {
        simpleStorageService.deleteStorageObject(bucketName, key);
        return Response.ok();
    }

}

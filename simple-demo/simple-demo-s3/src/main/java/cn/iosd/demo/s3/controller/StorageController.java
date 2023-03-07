package cn.iosd.demo.s3.controller;

import cn.iosd.starter.s3.domain.StorageObjectRequest;
import cn.iosd.starter.s3.domain.StorageObjectResponse;
import cn.iosd.starter.s3.service.SimpleStorageService;
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
    public Response<Bucket> creatBucket(String bucketName) {
        return Response.ok(simpleStorageService.creatBucket(bucketName));
    }

    @Operation(summary = "删除存储桶")
    @DeleteMapping("/bucket")
    public Response deleteBucket(String bucketName) {
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

    @Operation(summary = "上传文件")
    @PostMapping(value = "/object/upload")
    public Response<String> upload(@ModelAttribute MultipartFile file
            , @RequestParam(value = "bucketName") String bucketName
            , @RequestParam(value = "fileExtension") String fileExtension) throws IOException {
        String fileKey = RandomStringUtils.randomAlphabetic(12) + "." + fileExtension;
        return Response.ok(simpleStorageService.upload(file.getContentType(), file.getSize(),
                file.getInputStream(), bucketName, fileKey));
    }

    @Operation(summary = "删除文件")
    @DeleteMapping("/object")
    public Response<Boolean> deleteStorageObject(String bucketName
            , String objectName) {
        return Response.ok(simpleStorageService.deleteStorageObject(bucketName, objectName));
    }

}

package cn.iosd.demo.s3.controller;

import cn.iosd.demo.s3.service.DemoStorageService;
import cn.iosd.starter.web.domain.Response;
import com.amazonaws.services.s3.model.Bucket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "存储服务")
@RestController
@RequestMapping("/demo-base-s3/test/bucket")
public class DemoStorageController {

    @Autowired
    private DemoStorageService service;

    @Operation(summary = "获取存储桶列表")
    @GetMapping("/list")
    public Response<List<Bucket>> getListBuckets() {
        return Response.ok(service.getListBuckets());
    }
}

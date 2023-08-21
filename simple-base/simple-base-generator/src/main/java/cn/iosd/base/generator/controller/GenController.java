package cn.iosd.base.generator.controller;

import cn.iosd.base.generator.service.ProjectGenService;
import cn.iosd.base.generator.vo.ProjectGenVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

/**
 * @author ok1996
 */
@Tag(name = "代码生成")
@RestController
@RequestMapping("/simple-base-generator/generator")
@ConditionalOnProperty(value = "simple.base.generator.project.enabled", havingValue = "true")
public class GenController {
    @Autowired
    private ProjectGenService projectGenService;

    @Operation(summary = "项目初始化生成")
    @GetMapping(value = "/projectGen", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<FileSystemResource> projectGenZip(@ParameterObject ProjectGenVo req) throws IOException {
        Optional<FileSystemResource> generateCodeZipOptional = Optional.ofNullable(projectGenService.projectGenZip(req));
        FileSystemResource generateCodeZip = generateCodeZipOptional.orElseThrow(() -> new RuntimeException("代码生成失败"));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + generateCodeZip.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/x-zip-compressed")
                .contentLength(generateCodeZip.contentLength())
                .body(generateCodeZip);
    }

}

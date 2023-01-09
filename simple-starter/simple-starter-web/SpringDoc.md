### 访问路径

> host:port/swagger-ui/index.html
> host:port/doc.html

### 关闭SwaggerUi
~~~
springdoc:
  swagger-ui:
    enabled: false
~~~
### 关闭 /v3/api-docs endpoint
~~~
springdoc:
  apiDocs:
    enabled: false
~~~
### 在线文档
[https://springdoc.org/v2/#disabling-the-swagger-ui]

### 区别

| SpringFox                                   | SpringDoc                                                     |
|:--------------------------------------------|:--------------------------------------------------------------|
| @Api                                        | @Tag                                                          |
| @ApiIgnore                                  | @Parameter(hidden = true)or@Operation(hidden = true)or@Hidden |
| @ApiImplicitParam                           | @Parameter                                                    |
| @ApiImplicitParams                          | @Parameters                                                   |
| @ApiModel                                   | @Schema                                                       |
| @ApiModelProperty                           | @Schema                                                       |
| @ApiOperation(value = "foo", notes = "bar") | @Operation(summary = "foo", description = "bar")              |
| @ApiParam                                   | @Parameter                                                    |
| @ApiResponse(code = 404, message = "foo")   | ApiResponse(responseCode = "404", description = "foo")        |


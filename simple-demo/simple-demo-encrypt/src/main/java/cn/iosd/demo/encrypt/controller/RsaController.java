package cn.iosd.demo.encrypt.controller;

import cn.iosd.demo.encrypt.vo.PersonVo;
import cn.iosd.starter.encrypt.rsa.annotation.DecryptRequestParams;
import cn.iosd.starter.encrypt.rsa.annotation.EncryptResponseParams;
import cn.iosd.starter.encrypt.rsa.annotation.SecureParams;
import cn.iosd.starter.encrypt.rsa.annotation.UnEncrypted;
import cn.iosd.starter.encrypt.rsa.properties.RsaProperties;
import cn.iosd.starter.encrypt.rsa.utils.RsaUtils;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ok1996
 */
@Tag(name = "Rsa测试模块")
@RestController
@RequestMapping("/simple-demo-encrypt/rsa")
public class RsaController {
    @Autowired
    private RsaProperties rsaProperties;

    @Operation(summary = "注解测试-返回参数加密")
    @GetMapping(value = "/encryptResponse")
    @EncryptResponseParams
    public Response<String> encryptResponse(String reqString) {
        return Response.ok(reqString);
    }

    @Operation(summary = "注解测试-请求参数解密-POST")
    @DecryptRequestParams
    @PostMapping(value = "/decryptRequestParams")
    public Response<PersonVo> decryptRequestParams(@RequestBody PersonVo vo) {
        return Response.ok(vo);
    }

    @Operation(summary = "注解测试-请求参数解密-GET")
    @DecryptRequestParams
    @GetMapping(value = "/decryptRequestParams")
    public Response<PersonVo> decryptRequestParamsGet(PersonVo vo) {
        return Response.ok(vo);
    }

    @Operation(summary = "注解测试-请求参数解密及返回参数加密")
    @SecureParams
    @PostMapping(value = "/decryptAndEncrypt")
    public Response<PersonVo> decryptAndEncrypt(@RequestBody PersonVo vo) {
        return Response.ok(vo);
    }

    @Operation(summary = "工具类-加密测试")
    @GetMapping(value = "/encrypt")
    @UnEncrypted
    public String encrypt(String reqString) throws Exception {
        return RsaUtils.encrypt(reqString, rsaProperties.getPublicKey());
    }

    @Operation(summary = "工具类-解密测试")
    @GetMapping(value = "/decrypt")
    @UnEncrypted
    public String decrypt(String data) throws Exception {
        return RsaUtils.decrypt(data, rsaProperties.getPrivateKey());
    }

    @Operation(summary = "注解测试-测试全局是否开启-请求参数解密及返回参数加密")
    @PostMapping(value = "/decryptAndEncryptAll")
    public Response<PersonVo> decryptAndEncryptAll(@RequestBody PersonVo vo) {
        return Response.ok(vo);
    }
}

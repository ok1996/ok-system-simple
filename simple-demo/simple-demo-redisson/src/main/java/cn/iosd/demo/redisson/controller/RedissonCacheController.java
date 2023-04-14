package cn.iosd.demo.redisson.controller;

import cn.iosd.demo.redisson.vo.PersonVo;
import cn.iosd.starter.redisson.service.RedissonCacheService;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Tag(name = "缓存的常用操作")
@RequestMapping("/cache")
public class RedissonCacheController {
    @Autowired(required = false)
    private RedissonCacheService redissonCacheService;

    @Operation(summary = "Object-获取")
    @GetMapping("/object/{key}")
    public Response<PersonVo> getObject(@PathVariable String key) {
        return Response.ok(redissonCacheService.getObject(key));
    }

    @Operation(summary = "Object-保存")
    @PostMapping("/object/{key}")
    public Response<?> setObject(@PathVariable String key, @RequestBody PersonVo value) {
        redissonCacheService.setObject(key, value);
        return Response.ok();
    }

    @Operation(summary = "List-获取")
    @GetMapping("/list/{key}")
    public Response<List<PersonVo>> getList(@PathVariable String key) {
        return Response.ok(redissonCacheService.getList(key));
    }

    @Operation(summary = "List-保存")
    @PostMapping("/list/{key}")
    public Response<?> setList(@PathVariable String key, @RequestBody List<PersonVo> value) {
        redissonCacheService.setList(key, value);
        return Response.ok();
    }

    @Operation(summary = "Map-获取")
    @GetMapping("/map/{key}")
    public Response<Map<String, PersonVo>> getMap(@PathVariable String key) {
        return Response.ok(redissonCacheService.getMap(key));
    }

    @Operation(summary = "Map-保存")
    @PostMapping("/map/{key}")
    public Response<?> setMap(@PathVariable String key, @RequestBody PersonVo value) {
        Map save = new HashMap(2);
        save.put(key, value);
        redissonCacheService.setMap(key, save);
        return Response.ok();
    }

    @Operation(summary = "删除")
    @DeleteMapping("/{key}")
    public Response<?> delete(@PathVariable("key") String key) {
        redissonCacheService.delete(key);
        return Response.ok();
    }
}

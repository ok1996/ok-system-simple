package cn.iosd.demo.redisson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.iosd.starter.redisson.service.RedissonService;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ok1996
 */
@Slf4j
@RestController
@Tag(name = "分布式锁-测试请求")
@RequestMapping("/lock")
public class LockController {
    @Autowired(required = false)
    private RedissonService redissonService;

    /**
     * 商品库存
     */
    public static volatile Integer TOTAL = 1000000;

    /**
     * 商品库存-非锁测试
     */
    public static volatile Integer TOTAL_NOT_LOCK = 1000000;

    public final static String LOCK_NAME = "demo-redisson:commodityInventory";

    public final static String TRY_LOCK_NAME = "demo-redisson:commodityInventoryTry";

    @Operation(summary = "库存自减")
    @GetMapping("decrement")
    public Response<?> decrement() throws InterruptedException {
        redissonService.lock(LOCK_NAME, 10L);
        if (TOTAL > 0) {
            TOTAL--;
        }
        Thread.sleep(50);
        log.info("===lock===减完库存后,当前库存===" + TOTAL);
        //如果该线程还持有该锁，那么释放该锁。如果该线程不持有该锁，说明该线程的锁已到过期时间，自动释放锁
        if (redissonService.isHeldByCurrentThread(LOCK_NAME)) {
            redissonService.unlock(LOCK_NAME);
        }
        return Response.ok();
    }

    @Operation(summary = "库存自减-TryLock")
    @GetMapping("decrementTryLock")
    public Response<?> decrementTryLock() throws InterruptedException {
        //锁有效时间
        Long lease = 5L;
        //等待时间
        Long wait = 200L;
        if (redissonService.tryLock(TRY_LOCK_NAME, lease, wait)) {
            if (TOTAL > 0) {
                TOTAL--;
            }
            Thread.sleep(50);
            redissonService.unlock(TRY_LOCK_NAME);
            log.info("====tryLock===减完库存后,当前库存===" + TOTAL);
        } else {
            log.info("[ExecutorRedisson]获取锁失败");
        }
        return Response.ok();
    }

    @Operation(summary = "库存自减-未加锁")
    @GetMapping("decrementNotLock")
    public Response<?> notLock() throws InterruptedException {
        if (TOTAL_NOT_LOCK > 0) {
            TOTAL_NOT_LOCK--;
        }
        Thread.sleep(50);
        log.info("===notLock===减完库存后,当前库存===" + TOTAL_NOT_LOCK);
        return Response.ok();
    }
}

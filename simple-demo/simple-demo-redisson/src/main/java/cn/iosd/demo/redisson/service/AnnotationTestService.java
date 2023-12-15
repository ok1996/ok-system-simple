package cn.iosd.demo.redisson.service;

import cn.iosd.starter.redisson.annotation.DistributedIdempotent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ok1996
 */
@Slf4j
@Service
public class AnnotationTestService {

    @DistributedIdempotent(value = "demoIdempotent", param = "#keyName", message = "请求重复！", expireTime = 30, executionFinishedUnlock = false)
    public void distributedIdempotent(String keyName) {
        log.info("distributedIdempotent:{} ", keyName);
    }

}

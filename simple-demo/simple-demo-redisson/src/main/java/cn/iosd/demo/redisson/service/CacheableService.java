package cn.iosd.demo.redisson.service;

import cn.iosd.starter.redisson.domain.CacheName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author ok1996
 */
@Slf4j
@Service
public class CacheableService {

    @Cacheable(key = "#keyName + 'TenMinutes'", cacheNames = CacheName.EXPIRES_AFTER_TEN_MINUTES, condition = "#keyName.length()>2")
    public String annotateTestCacheNameTenMinutes(String keyName) {
        log.info("cacheExpiresAfterTenMinutes:{}", keyName);
        return keyName;
    }

    @Cacheable(key = "#keyName + 'TenSecond'", cacheNames = "cacheExpiresAfterTenSecond", condition = "#keyName.length()>2")
    public String annotateTestCacheNameTenSecond(String keyName) {
        log.info("cacheExpiresAfterTenSecond:{}", keyName);
        return keyName;
    }
}

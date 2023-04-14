package cn.iosd.starter.redisson.service;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redisson 缓存服务类，用于封装 Redisson 对象缓存的常用操作
 *
 * @author ok1996
 */
public class RedissonCacheService {
    private Redisson redisson;

    public RedissonCacheService(RedissonManager redissonManager) {
        this.redisson = redissonManager.getRedisson();
    }


    /**
     * 将值存储到缓存中
     *
     * @param key   缓存键
     * @param value 缓存值
     * @param ttl   过期时间（单位：秒）
     */
    public void setObject(String key, Object value, long ttl) {
        RBucket<Object> bucket = redisson.getBucket(key);
        bucket.set(value, ttl, TimeUnit.SECONDS);
    }

    /**
     * 将值存储到缓存中-永久保存
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    public void setObject(String key, Object value) {
        RBucket<Object> bucket = redisson.getBucket(key);
        bucket.set(value);
    }

    /**
     * 从缓存中获取值
     *
     * @param key 缓存键
     * @return 缓存值
     */
    public <V> V getObject(String key) {
        RBucket<V> bucket = redisson.getBucket(key);
        return bucket.get();
    }


    /**
     * 判断缓存中是否存在指定的键
     *
     * @param key 缓存键
     * @return true表示存在，false表示不存在
     */
    public boolean existsObject(String key) {
        RBucket<Object> bucket = redisson.getBucket(key);
        return bucket.isExists();
    }

    /**
     * 将列表存储到缓存中
     *
     * @param key  缓存键
     * @param list 缓存值（List类型）
     * @param ttl  过期时间（单位：秒）
     */
    public <V> void setList(String key, List<V> list, long ttl) {
        RList<V> rList = redisson.getList(key);
        rList.addAll(list);
        rList.expire(ttl, TimeUnit.SECONDS);
    }

    /**
     * 将列表存储到缓存中-永久保存
     *
     * @param key  缓存键
     * @param list 缓存值（List类型）
     */
    public <V> void setList(String key, List<V> list) {
        RList<V> rList = redisson.getList(key);
        rList.addAll(list);
    }

    /**
     * 从缓存中获取列表
     *
     * @param key 缓存键
     * @return 缓存值（List类型）
     */
    public <V> List<V> getList(String key) {
        RList<V> rList = redisson.getList(key);
        return rList.readAll();
    }


    /**
     * 判断缓存中是否存在指定的列表
     *
     * @param key 缓存键
     * @return true表示存在，false表示不存在
     */
    public boolean existsList(String key) {
        RList<Object> rList = redisson.getList(key);
        return rList.isExists();
    }

    /**
     * 将哈希存储到缓存中
     *
     * @param key 缓存键
     * @param map 缓存值（Map类型）
     * @param ttl 过期时间（单位：秒）
     */
    public <K, V> void setMap(String key, Map<K, V> map, long ttl) {
        RMap<K, V> rMap = redisson.getMap(key);
        rMap.putAll(map);
        rMap.expire(ttl, TimeUnit.SECONDS);
    }

    /**
     * 将哈希存储到缓存中-永久保存
     *
     * @param key 缓存键
     * @param map 缓存值（Map类型）
     */
    public <K, V> void setMap(String key, Map<K, V> map) {
        RMap<K, V> rMap = redisson.getMap(key);
        rMap.putAll(map);
    }

    /**
     * 从缓存中获取哈希
     *
     * @param key 缓存键
     * @return 缓存值（Map类型）
     */
    public <K, V> Map<K, V> getMap(String key) {
        RMap<K, V> rMap = redisson.getMap(key);
        return rMap.readAllMap();
    }


    /**
     * 判断缓存中是否存在指定的哈希
     *
     * @param key 缓存键
     * @return true表示存在，false表示不存在
     */
    public boolean existsMap(String key) {
        RMap<Object, Object> rMap = redisson.getMap(key);
        return rMap.isExists();
    }


    /**
     * 从缓存中删除值
     *
     * @param key 缓存键
     */
    public void delete(String key) {
        redisson.getBucket(key).delete();
    }
}

package com.waston.seckill.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/10/18 21:05
 */
public abstract class BaseRedis<T> {

    @Autowired
    protected RedisTemplate<String,Object> redisTemplate;

    @Resource
    protected HashOperations<String,String,T> hashOperations;

    protected abstract String getRedisKey();

    public void put(String key,T domain,long expire){
        hashOperations.put(getRedisKey(),key,domain);
        if(expire != -1){
            redisTemplate.expire(getRedisKey(),expire,TimeUnit.SECONDS);
        }
    }

    public T get(String key) {
        return hashOperations.get(getRedisKey(), key);
    }
}

package com.waston.seckill.redis;

import com.waston.seckill.model.User;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/10/18 21:11
 */
@Repository
public class UserRedis extends BaseRedis<User> {

    private static final String REDIS_KEY = "com.waston.seckill.redis.UserRedis";

    @Override
    protected String getRedisKey() {
        return REDIS_KEY;
    }
}

package com.waston.seckill;

import com.waston.seckill.Servise.UserService;
import com.waston.seckill.VO.UserVO;
import com.waston.seckill.model.User;
import com.waston.seckill.redis.UserRedis;
import com.waston.seckill.util.MD5Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.fail;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/10/15 23:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    public UserService userService;

    @Autowired
    public UserRedis userRedis;

    @Test
    public void test(){
        User user = new User("wss","0000");
        //User newuser = save(user);
        Assert.assertNotNull(userService.regist(user));
//        fail("Not");
    }

    @Test
    public void testGetUser(){
        Assert.assertNotNull(userService.getUser("666"));
    }

    @Test
    public void testPassword(){
        UserVO user = userService.getUser("666");
        String password = MD5Util.inputToDb("0000",user.getDbflag());
        Assert.assertEquals(password,user.getPassword());
    }

    @Test
    public void testPutRedis(){
        User user = new User("wjh","1234");
        userRedis.put(user.getUsername(),user,-1);
    }
}

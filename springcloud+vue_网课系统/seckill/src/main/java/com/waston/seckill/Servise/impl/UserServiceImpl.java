package com.waston.seckill.Servise.impl;

import com.waston.seckill.Servise.UserService;
import com.waston.seckill.VO.UserVO;
import com.waston.seckill.model.User;
import com.waston.seckill.redis.UserRedis;
import com.waston.seckill.repository.UserRpository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/10/15 22:43
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRpository userRpository;

    @Autowired
    public UserRedis userRedis;

    @Override
    public User regist(User user) {
        return userRpository.saveAndFlush(user);
    }

    @Override
    public UserVO getUser(String username) {
        UserVO userVO = new UserVO();
        User user = userRedis.get("username");
        if(user == null){
            user = userRpository.findByUsername(username);
            if(user != null){
                userRedis.put(user.getUsername(),user,-1);
            }else {
                return null;
            }
        }
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }

    @Override
    public void saveUserToRedisByToken(UserVO temp, String token) {
        User user = new User();
        BeanUtils.copyProperties(temp,user);
        userRedis.put(token,user,3600);
    }

    @Override
    public Object getUserFromRedisByToken(String token) {
        return userRedis.get(token);
    }
}

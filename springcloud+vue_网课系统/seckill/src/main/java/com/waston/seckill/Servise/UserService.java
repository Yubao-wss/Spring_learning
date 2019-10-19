package com.waston.seckill.Servise;

import com.waston.seckill.VO.UserVO;
import com.waston.seckill.model.User;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/10/15 22:42
 */
public interface UserService {
    public User regist(User user);
    public UserVO getUser(String username);

    void saveUserToRedisByToken(UserVO temp, String token);

    Object getUserFromRedisByToken(String token);
}

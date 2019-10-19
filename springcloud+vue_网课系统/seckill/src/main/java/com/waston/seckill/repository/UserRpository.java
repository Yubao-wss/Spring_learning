package com.waston.seckill.repository;

import com.waston.seckill.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/10/15 22:44
 */
@Repository
public interface UserRpository extends JpaRepository<User,String> {
    public User findByUsernameAndPassword(String username,String Password);

    public User findByUsername(String username);

    User saveAndFlush(User user);
}

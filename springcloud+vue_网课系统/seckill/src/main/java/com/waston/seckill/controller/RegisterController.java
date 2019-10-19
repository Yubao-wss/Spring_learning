package com.waston.seckill.controller;

import com.waston.seckill.Servise.UserService;
import com.waston.seckill.model.User;
import com.waston.seckill.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/10/16 21:59
 */
@Controller
public class RegisterController {

    @Autowired
    public UserService userService;

    @RequestMapping(value="/reg",method = RequestMethod.GET)
    public ModelAndView toRegister(ModelAndView model){
        User user = new User();
        return new ModelAndView("register").addObject(user);
    }

    @RequestMapping(value="/register",method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ModelAndView("register");
        }

        //加盐加密
        String salt = "waston";
        String newPassword = MD5Util.backToDb(user.getPassword(),salt);
        user.setPassword(newPassword);
        user.setDbflag(salt);

        user.setId(2018);

        userService.regist(user);
        return new ModelAndView("register");

    }
}

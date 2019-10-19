package com.waston.seckill.controller;

import com.waston.seckill.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/10/19 20:28
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String home(Model model,User user){

        //检测是否获取到user
        System.out.println("888888888888888888888"+user.getUsername());
        model.addAttribute("user",new User());
        return "home";
    }
}

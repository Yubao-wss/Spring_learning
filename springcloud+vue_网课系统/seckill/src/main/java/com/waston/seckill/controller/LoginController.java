package com.waston.seckill.controller;

import com.waston.seckill.Servise.UserService;
import com.waston.seckill.VO.UserVO;
import com.waston.seckill.model.User;
import com.waston.seckill.util.MD5Util;
import com.waston.seckill.util.UUIDUtil;
import com.waston.seckill.util.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/10/16 21:59
 */
@Controller
public class LoginController {

    @Autowired
    public UserService userService;

    @RequestMapping(value="/login",method = RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("title","登陆页面");
        return "login";
    }

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String login(@ModelAttribute(value = "user")@Valid User user,BindingResult bindingResult,HttpSession session,String code,Model model,HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "login";
        }

        //验证码
        String sessionCode = (String) session.getAttribute("code");
        if(!StringUtils.equalsIgnoreCase(code,sessionCode)){
            model.addAttribute("message","验证码不匹配");
            return "login";
        }

        //用户存在检测
        UserVO temp = userService.getUser(user.getUsername());
        //密码是否一致
        if(temp != null){
            String inputPassword = MD5Util.inputToDb(user.getPassword(),temp.getDbflag());
            if(temp.getPassword().equals(inputPassword)){
                //保存session
                //session.setAttribute("user",temp);

                //生成 token 作为 Redis key
                String token = UUIDUtil.getUUID();
                userService.saveUserToRedisByToken(temp,token);

                //把token存到cookie中
                Cookie cookie = new Cookie("token",token);
                cookie.setMaxAge(3600);
                cookie.setPath("/");
                response.addCookie(cookie);

                return "redirect:/home";
            }else {
                return "login";
            }
        }else {
            return "login";
        }
    }

    /**
     * 响应验证页面
     */
    @RequestMapping(value = "/validateCode")
    public String validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        //禁止图像缓存
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);

        HttpSession session = request.getSession();

        ValidateCode vCode = new ValidateCode(120,34,5,100);
        session.setAttribute("code",vCode.getCode());
        vCode.write(response.getOutputStream());
        return null;
    }
}

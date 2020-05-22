package com.swjd.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjd.bean.User;
import com.swjd.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Resource
    UserServiceImpl userService;

    //去登录
    @RequestMapping("/toLogin")
    public String toLogin(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "login";
    }

    //做登录
    @RequestMapping("/doLogin")
    public String doLogin(User user, Model model, HttpSession session){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("uname",user.getUname());
        queryWrapper.eq("password",user.getPassword());
        User u=userService.getOne(queryWrapper);
        if (u!=null){
            //账号密码正确
            if (u.getFlag().equals("1")){
                //登录成功,把用户名存到session里
                session.setAttribute("activeName",u.getUname());

                return "redirect:/customerController/toMain";
            }else {
                //账号禁用
                model.addAttribute("user",user);
                model.addAttribute("errorMsg","该账号为禁用状态,请联系管理员！");
                return "login";
            }
        }else {
            //账号密码错误
            model.addAttribute("user",user);
            model.addAttribute("errorMsg","账号或密码错误");
            return "login";
        }
    }

    //去main.jsp
    @RequestMapping("/toMain")
    public String toMain(){
        return "main";
    }

}

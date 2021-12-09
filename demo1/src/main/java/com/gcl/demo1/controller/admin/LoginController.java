package com.gcl.demo1.controller.admin;

import com.gcl.demo1.entity.jpa.User;
import com.gcl.demo1.service.jpa.DayCountService;
import com.gcl.demo1.service.jpa.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/10 21:19
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private DayCountService dayCountService;

    /**
     * 登录页面
     * @return
     */
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){

//        System.out.println("进入登录页面");

        User user = userService.checkUser(username,password);
        //如果用户名和密码都能找到，说明登录成功
        if (user!=null){
            //登录成功之后将用户信息放到session里面去，方便之后的登出操作
            //在将用户信息放到session之前为了用户的安全先将密码设为空
            user.setPassword(null);
            session.setAttribute("user",user);
//            System.out.println("登录成功");
            //登录成功之后跳转到首页
            return "admin/index";
        }
        else{
            attributes.addFlashAttribute("message","用户名和密码错误");
//            System.out.println("登录失败");
            return "redirect:/admin";
        }
    }

    /**
     * 退出系统
     * @param session
     * @return
     */
    @GetMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("user");
        System.out.println("登出成功！！！！！");
        return "redirect:/admin";
    }
}

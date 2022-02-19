package com.gcl.demo1.controller.admin;

import com.gcl.demo1.entity.User;
import com.gcl.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 登录页面
     * @return
     */
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }


    @PostMapping("/login")
    public ModelAndView login(@RequestParam(value = "username") String username,
                              @RequestParam(value = "password") String password,
                              @RequestParam(value = "tryCode") String tryCode,
                              HttpSession session,
                              HttpServletRequest request){

        User user = userService.login(username,password);
        ModelAndView modelAndView = new ModelAndView();
        //如果用户名和密码都能找到，说明登录成功
        if (user!=null){
            //正确的验证码
            String rightCode = (String) request.getSession().getAttribute("rightCode");
            //如果两个验证码并不相等，则进入错误阶段
            if (!tryCode.equals(rightCode)) {
                modelAndView.addObject("info", "验证码错误,请再输一次!");
                //验证码错误的话直接回到login页面，并将错误信息info传输到该页面
                modelAndView.setViewName("admin/login");
                return modelAndView;
            } else { //如果两个验证码相等，则重定向到首页
                //登录成功之后将用户信息放到session里面去，方便之后的登出操作
                //在将用户信息放到session之前为了用户的安全先将密码设为空
                user.setPassword(null);
                session.setAttribute("user", user);
                //登录成功直接重定向到首页
                modelAndView.setViewName("admin/index");
                return modelAndView;
            }
        } else{
            modelAndView.addObject("info", "用户名或密码错误");
            modelAndView.setViewName("admin/login");
            return modelAndView;
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
        return "redirect:/admin";
    }
}

package com.gcl.demo1.controller;

//import com.gcl.demo1.entity.jpa.User;
import com.gcl.demo1.service.jpa.UserService;
//import com.gcl.demo1.utils.InitRedisData;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/16 17:40
 */
@Controller
public class AboutShowController {

//    @Autowired
//    private InitRedisData initRedisData;
//
//    @Autowired
//    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private UserService userService;

    /**
     * 关于我页面
     * @return
     */
    @GetMapping("/about")
    public String about(Model model) {
        //获取Redis中的键
//        String key = initRedisData.initAboutShow();
        //从Redis中获取值
//        model.addAttribute("user", User.stringToUser(redisTemplate.opsForValue().get(key)));

        model.addAttribute("user",userService.getUser());
        return "about";
    }
}

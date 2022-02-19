package com.gcl.demo1.controller;

import com.gcl.demo1.entity.User;
import com.gcl.demo1.service.HobbyService;
import com.gcl.demo1.service.TagService;
import com.gcl.demo1.utils.InitRedisData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author：小关同学
 * @date: 2020/12/16 17:40
 */
@Controller
public class AboutShowController {

    @Autowired
    private InitRedisData initRedisData;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

//    @Autowired
//    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    private HobbyService hobbyService;

    /**
     * 关于我页面
     * @return
     */
    @GetMapping("/about")
    public String about(Model model) {
        //获取Redis中的键
        String key = initRedisData.initAboutShow();

        //从Redis中获取用户信息
        User user = User.stringToUser(redisTemplate.opsForValue().get(key));
        user.setPassword(null);
        user.setBlogs(null);
        model.addAttribute("user", user);
        System.out.println("redis1获取到的数据"+user.toString());

        //从数据库取值的做法
//        User user = userService.getUser("小关同学");
//        user.setPassword(null);
//        user.setBlogs(null);
        //用户信息
//        model.addAttribute("user",user);


        //标签
        model.addAttribute("tags", tagService.listTagTop(10));
        //个人爱好
        model.addAttribute("hobbies", hobbyService.findHobbyByUserId(user.getId()));
        return "about";
    }
}

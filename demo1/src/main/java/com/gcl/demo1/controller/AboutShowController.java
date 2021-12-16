package com.gcl.demo1.controller;

//import com.gcl.demo1.utils.InitRedisData;
import com.gcl.demo1.entity.mybatis.User;
import com.gcl.demo1.service.mybatis.HobbyService;
import com.gcl.demo1.service.mybatis.MTagService;
import com.gcl.demo1.service.mybatis.MUserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author：小关同学
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
    private MUserService mUserService;

    @Autowired
    private MTagService mTagService;

    @Autowired
    private HobbyService hobbyService;

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
        User user = mUserService.getUser("关昌隆");
        //用户信息
        model.addAttribute("user",user);
        //标签
        model.addAttribute("tags", mTagService.listTagTop(10));
        //个人爱好
        model.addAttribute("hobbies",hobbyService.findHobbyByUserId(user.getId()));
        return "about";
    }
}

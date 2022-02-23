package com.gcl.demo1.controller;

import com.gcl.demo1.entity.User;
import com.gcl.demo1.service.HobbyService;
import com.gcl.demo1.service.TagService;
import com.gcl.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService userService;

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
        User user = userService.getUserByNickname("小关同学");
        //用户信息
        model.addAttribute("user", user);
        //标签
        model.addAttribute("tags", tagService.listTagTop(10));
        //个人爱好
        model.addAttribute("hobbies", hobbyService.findHobbyByUserId(user.getId()));
        return "about";
    }
}

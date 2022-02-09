package com.gcl.demo1.controller;

import com.gcl.demo1.entity.mybatis.Message;
import com.gcl.demo1.entity.mybatis.User;
import com.gcl.demo1.service.mybatis.MMessageService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author 小关同学
 * @create 2021/10/3
 * 留言板页面
 */
@Controller
public class MessageController {

    @Autowired
    private MMessageService mMessageService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/message/messageBoard")
    public String messageBoard(){
        return "message_board";
    }

    @PostMapping("/message/saveMessages")
    public String post(Message message, HttpSession session) {
        message.setCreateTime(new Date());
        User user = (User) session.getAttribute("user");
        if (user != null) {
            message.setAvatar(user.getAvatar());
            message.setRole(0);
        } else {
            message.setAvatar(avatar);
            message.setRole(1);
        }
        mMessageService.saveMessage(message);
        return "redirect:/message";
    }

    @GetMapping(value ="/messages")
    public String Message_board(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                @RequestParam(value = "size",defaultValue = "8")Integer size,
                                Model model) {
        PageInfo<Message> messages = mMessageService.listMessage(pageNum,size);
        model.addAttribute("messages",messages);
        return "message_board";
    }

    @GetMapping(value ="/message")
    public String message(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                          @RequestParam(value = "size",defaultValue = "8")Integer size,
                          Model model) {
        PageInfo<Message> messages = mMessageService.listMessage(pageNum,size);
        model.addAttribute("messages",messages);
        return "message_board :: messageList";//将数据返回message_board页面的th:fragment="messageList"片段，实现局部刷新
    }
}

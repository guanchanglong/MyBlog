package com.gcl.demo1.controller;

import com.gcl.demo1.entity.jpa.Message;
import com.gcl.demo1.entity.jpa.User;
import com.gcl.demo1.service.jpa.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author 小关同学
 * @create 2021/10/3
 * 留言板页面
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/messageBoard")
    public String messageBoard(){
        return "message_board";
    }

    @PostMapping("/saveMessages")
    public String post(Message message, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            message.setAvatar(user.getAvatar());
            message.setAdminMessage(2);
        } else {
            message.setAdminMessage(0);
            message.setAvatar(avatar);
        }
        messageService.saveMessage(message);
        messageService.findParentMessage(message);
        return "redirect:/message";
    }

    @RequestMapping(value ="/messages" ,method = RequestMethod.GET)
    public String Message_board(@RequestParam(value = "key",required = false)String key,
                                @RequestParam(value = "page",defaultValue = "1")Integer page,
                                @RequestParam(value = "rows",defaultValue = "8")Integer rows,
                                @RequestParam(value = "sortBy",defaultValue = "createTime")String sortBy,
                                @RequestParam(value = "desc",required = false)Boolean desc,
                                Model model) {
        Sort sort = Sort.by(Sort.Direction.DESC,sortBy);
        Pageable pageable = PageRequest.of(page-1, rows, sort);

        Page<Message> messages = messageService.listMessage(pageable);
        boolean a = messages.hasPrevious(); //判断是否为首页
        boolean b = messages.hasNext();//判断是否为尾页
        model.addAttribute("a",a);
        model.addAttribute("b",b);
        model.addAttribute("messages",messageService.listMessage(pageable));
        return "message_board";
    }

    @RequestMapping(value ="/message" ,method = RequestMethod.GET)
    public String message(@RequestParam(value = "key",required = false)String key,
                          @RequestParam(value = "page",defaultValue = "1")Integer page,
                          @RequestParam(value = "rows",defaultValue = "10")Integer rows,
                          @RequestParam(value = "sortBy",defaultValue = "createTime")String sortBy,
                          @RequestParam(value = "desc",required = false)Boolean desc,
                                    Model model) {
        Sort sort = Sort.by(Sort.Direction.DESC,sortBy);
        Pageable pageable = PageRequest.of(page-1, rows, sort);
        Page<Message> messages = messageService.listMessage(pageable);
        boolean a = messages.hasPrevious(); //判断是否为首页
        boolean b = messages.hasNext();//判断是否为尾页
        model.addAttribute("a",a);
        model.addAttribute("b",b);
        model.addAttribute("messages",messageService.listMessage(pageable));
        return "message_board :: messageList";//将数据返回message_board页面的th:fragment="messageList"片段，实现局部刷新
    }
}

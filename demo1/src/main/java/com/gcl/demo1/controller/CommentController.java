package com.gcl.demo1.controller;

import com.gcl.demo1.entity.Comment;
import com.gcl.demo1.entity.User;
import com.gcl.demo1.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author：小关同学
 * @date: 2020/12/16 17:41
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Value("${comment.avatar}")
    private String avatar;

    /**
     * 评论显示
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable int blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }


    /**
     * 提交评论
     * @param comment
     * @param session
     * @return
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        comment.setCreateTime(new Date());
        int blogId = comment.getBlogId();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setRole(0);
        } else {
            comment.setAvatar(avatar);
            comment.setRole(1);
        }
        System.out.println(comment.getAvatar());
        System.out.println(comment.getContent());
        System.out.println(comment.getEmail());
        System.out.println(comment.getNickname());
        //表单判空验证问题还未解决
        //昵称、邮箱、内容都可以为空，这不合理，得在前端解决
//        commentService.insertComment(comment);
        return "redirect:/comments/" + blogId;
    }
}

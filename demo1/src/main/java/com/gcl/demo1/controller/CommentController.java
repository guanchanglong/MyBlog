package com.gcl.demo1.controller;

import com.gcl.demo1.dao.mybatis.MCommentDao;
import com.gcl.demo1.entity.jpa.Comment;
import com.gcl.demo1.entity.jpa.User;
import com.gcl.demo1.service.jpa.BlogService;
import com.gcl.demo1.service.jpa.CommentService;
import com.gcl.demo1.service.mybatis.MCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author：小关同学
 * @date: 2020/12/16 17:41
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MCommentService mCommentService;

    @Autowired
    private BlogService blogService;

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
        model.addAttribute("comments", mCommentService.listCommentByBlogId(blogId));
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
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

}

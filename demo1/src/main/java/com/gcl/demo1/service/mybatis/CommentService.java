package com.gcl.demo1.service.mybatis;


import com.gcl.demo1.entity.jpa.Comment;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/16 17:34
 */
public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}

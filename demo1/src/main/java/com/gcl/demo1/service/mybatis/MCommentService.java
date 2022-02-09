package com.gcl.demo1.service.mybatis;


import com.gcl.demo1.entity.mybatis.Comment;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/16 17:34
 */
public interface MCommentService {

    List<Comment> listCommentByBlogId(int blogId);

    void insertComment(Comment comment);

    PageInfo<Comment> listMessage(int pageNum, int size);

}

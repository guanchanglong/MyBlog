package com.gcl.demo1.entity.mybatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/7
 */
public class Comment {

    private int id;
    //昵称
    private String nickname;
    //邮箱
    private String email;
    //评论内容
    private String content;
    //头像
    private String avatar;
    //评论时间
    private Date createTime;
    //评论对应的博客
    //一对一
    //一个评论对应一篇博客
    private Blog blog;
    //子评论
    //一个父评论对应多个子评论
    private List<Comment> replyComments = new ArrayList<>();
    //对应的父评论
    private Comment parentComment;
    //角色
    private int role;
}

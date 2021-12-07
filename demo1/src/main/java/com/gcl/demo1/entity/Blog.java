package com.gcl.demo1.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/7
 */
public class Blog {

    //博客的id
    private Long id;
    //博客的标题
    private String title;
    //博文内容
    private String content;
    //博客的封面图片
    private String firstPicture;
    //博客的
    private String flag;
    //博客的浏览人数
    private Integer views;
    //博客是否开启赞赏
    private boolean appreciation;
    //博客是否开启分享
    private boolean shareStatement;
    //博客是否开启评论
    private boolean commentable;
    //博客是否发布
    private boolean published;
    //博客是否开启推荐
    private boolean recommend;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    private String tagIds;
    //博客简介
    private String description;
    private String year;
    private User user;
    //类型
    //多对一
    //一个类型对应着多篇文章
    private Type type;
    //评论
    //一对多
    //一篇文章对应多个评论
    private List<Comment> comments = new ArrayList<>();
    //标签
    //多对多
    //多个标签对应着多篇文章
    private List<Tag> tags = new ArrayList<>();


}

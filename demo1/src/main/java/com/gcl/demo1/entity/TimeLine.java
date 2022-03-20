package com.gcl.demo1.entity;

import java.util.Date;

/**
 * @Author 小关同学
 * @Create 2022/3/19 16:47
 * 时间轴
 */
public class TimeLine {
    //主键
    private int id;
    //内容
    private String content;
    //时间
    private Date publicTime;
    //图片
    private String picture;
    //是否发布
    private boolean published;
    //用户id
    private int userId;
    //用户
    private User user;

    public TimeLine() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(Date publicTime) {
        this.publicTime = publicTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "TimeLine{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", publicTime=" + publicTime +
                ", picture='" + picture + '\'' +
                ", published=" + published +
                ", userId=" + userId +
                '}';
    }
}

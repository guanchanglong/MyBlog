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
    private int blogId;

    private int parentCommentId;
    //子评论
    //一个父评论对应多个子评论
    private List<Comment> replyComments = new ArrayList<>();
    //对应的父评论
    private Comment parentComment;
    //角色
    private int role;

    public int getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(int parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", blogId=" + blogId +
                ", parentCommentId=" + parentCommentId +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                ", role=" + role +
                '}';
    }

}

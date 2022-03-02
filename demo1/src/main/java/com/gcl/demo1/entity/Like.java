package com.gcl.demo1.entity;

/**
 * @Author 小关同学
 * @Create 2022/3/1 9:21
 * 点赞表
 */
public class Like {

    private int id;
    private int blogId;
    private String viewerIP;
    private boolean like;
    private boolean unLike;

    public Like() {
    }

    public Like(int blogId, String viewerIP, boolean like, boolean unLike) {
        this.blogId = blogId;
        this.viewerIP = viewerIP;
        this.like = like;
        this.unLike = unLike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getViewerIP() {
        return viewerIP;
    }

    public void setViewerIP(String viewerIP) {
        this.viewerIP = viewerIP;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isUnLike() {
        return unLike;
    }

    public void setUnLike(boolean unLike) {
        this.unLike = unLike;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", blogId=" + blogId +
                ", viewerIP='" + viewerIP + '\'' +
                ", like=" + like +
                ", unLike=" + unLike +
                '}';
    }
}

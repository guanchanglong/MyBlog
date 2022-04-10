package com.gcl.demo1.entity;

import java.util.Date;

/**
 * @Author 小关同学
 * @Create 2022/4/10 9:46
 */
public class Picture {

    //主键
    private int id;
    //图片日期
    private String date;
    //修改时间
    private Date createTime;
    //图片标题
    private String title;
    //图片拍摄地址
    private String address;
    //图片简介
    private String info;
    //图片
    private String picture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

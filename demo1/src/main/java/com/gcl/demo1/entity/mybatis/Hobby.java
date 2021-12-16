package com.gcl.demo1.entity.mybatis;

/**
 * @author 小关同学
 * @create 2021/12/16
 */
public class Hobby {
    private int id;
    private String hobbyName;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHobbyName() {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

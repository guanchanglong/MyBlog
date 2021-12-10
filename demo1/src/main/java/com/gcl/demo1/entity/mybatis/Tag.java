package com.gcl.demo1.entity.mybatis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/7
 */
public class Tag {

    private int id;
    //标签名称
    private String name;
    //对应的博客
    private List<Blog> blogs = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
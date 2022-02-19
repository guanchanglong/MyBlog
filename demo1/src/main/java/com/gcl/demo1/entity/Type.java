package com.gcl.demo1.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/7
 */
public class Type implements Comparable<Type>{

    private int id;

    private String name;

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

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }


    /**
     * 实现Comparable接口内方法进行排序
     * @param type
     * @return
     */
    @Override
    public int compareTo(Type type) {
        //升序
//        return blogs.size()-type.getBlogs().size();

        //降序
        return type.getBlogs().size() - blogs.size();
    }
}

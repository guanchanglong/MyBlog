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
}

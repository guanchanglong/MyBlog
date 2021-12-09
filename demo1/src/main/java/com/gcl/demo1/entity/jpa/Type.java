package com.gcl.demo1.entity.jpa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/8 23:01
 */
@Data
@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;

    //一对多（多表查询）
    //mappedBy = "type"说明type是被维护Blog和Type之间的关系
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

    public Type(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    /**
     * 字符串转Type对象
     * @param param 待转换的字符串
     * @return Type对象
     */
    public static Type stringToType(String param){
        JSONObject json = JSONObject.parseObject(param);
        return JSON.toJavaObject(json,Type.class);
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

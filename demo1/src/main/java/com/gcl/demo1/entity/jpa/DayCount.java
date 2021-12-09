package com.gcl.demo1.entity.jpa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 小关同学
 * @create 2021/10/5
 */
@Data
@Entity
@Table(name = "t_day_count")
public class DayCount {

    @Id
    @GeneratedValue
    private Long id;

    private String day;

    private long count;

    public DayCount(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    /**
     * 字符串转DayCount对象
     * @param param 待转换的字符串
     * @return DayCount对象
     */
    public static DayCount stringToDayCount(String param){
        JSONObject json = JSONObject.parseObject(param);
        return JSON.toJavaObject(json,DayCount.class);
    }
}

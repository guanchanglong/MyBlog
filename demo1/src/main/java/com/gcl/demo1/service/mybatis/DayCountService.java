package com.gcl.demo1.service.mybatis;


import com.gcl.demo1.entity.jpa.DayCount;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/10/5
 */
public interface DayCountService {
    List<String> getDates();

    void saveDayCount(DayCount dayCount);

    String getDateNewest();

    List<DayCount> getAll();

    List<Long> getCounts();

    long getTodayCount();
}

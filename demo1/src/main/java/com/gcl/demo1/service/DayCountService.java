package com.gcl.demo1.service;

import com.gcl.demo1.entity.DayCount;

import java.util.List;

/**
 * @Author 小关同学
 * @Create 2022/2/9 9:17
 */
public interface DayCountService {
    List<Integer> getCounts();

    void getCountsToUpdateRedis();

    List<DayCount> findAll();

    void updateDayCountById(DayCount dayCount);

    void updateDayCountOfCountById(int id, int count);

    DayCount findDayCountByDay(String day);
}

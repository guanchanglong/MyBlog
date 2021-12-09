package com.gcl.demo1.dao.mybatis;

import com.gcl.demo1.entity.jpa.DayCount;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/10/5
 */
public interface MDayCountDao {

    List<String> findAllByDay();

    DayCount findAllById(long id);

    void updateNewestDay(Long id);

    List<Long> getCounts();


}

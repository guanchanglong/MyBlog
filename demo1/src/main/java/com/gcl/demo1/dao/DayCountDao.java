package com.gcl.demo1.dao;

import com.gcl.demo1.entity.DayCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/10/5
 */
@Mapper
@Repository
public interface DayCountDao {

    DayCount findAllById(long id);

    void updateNewestDay(@Param("dayId") int dayId);

    List<DayCount> findAllDayCount();

    void updateDayCountById(@Param("dayCount") DayCount dayCount);

    void updateDayCountOfCountById(@Param("id") int id, @Param("count") int count);

    DayCount findDayCountByDay(@Param("day") String day);

}

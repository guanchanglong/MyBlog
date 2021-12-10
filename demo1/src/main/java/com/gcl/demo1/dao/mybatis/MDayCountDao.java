package com.gcl.demo1.dao.mybatis;

import com.gcl.demo1.entity.jpa.DayCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/10/5
 */
@Mapper
public interface MDayCountDao {

    List<String> findAllByDay();

    DayCount findAllById(long id);

    void updateNewestDay(@Param("dayId") int dayId);

    List<Long> getCounts();


}

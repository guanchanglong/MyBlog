package com.gcl.demo1.dao;

import com.gcl.demo1.entity.TimeLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author 小关同学
 * @Create 2022/3/19 16:51
 */
@Mapper
@Repository
public interface TimeLineDao {

    List<TimeLine> findAllTimeLine();

    List<TimeLine> findTimeLineByPublished();

    TimeLine findTimeLineById(@Param("id") int id);

    void insertTimeLine(@Param("timeLine") TimeLine timeLine);

    void updateTimeLineById(@Param("timeLine") TimeLine timeLine);

    void deleteTimeLineById(@Param("id") int id);
}

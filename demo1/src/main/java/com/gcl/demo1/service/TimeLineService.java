package com.gcl.demo1.service;

import com.gcl.demo1.entity.TimeLine;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author 小关同学
 * @Create 2022/3/19 16:52
 */
public interface TimeLineService {

    PageInfo<TimeLine> findAllTimeLine(int pageNum, int size);

    List<TimeLine> findAllTimeLine();

    TimeLine findTimeLineById(int id);

    void insertTimeLine(TimeLine timeLine);

    void updateTimeLineById(TimeLine timeLine);

    void deleteTimeLineById(int id);
}

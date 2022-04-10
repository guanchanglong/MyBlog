package com.gcl.demo1.service.impl;

import com.gcl.demo1.dao.TimeLineDao;
import com.gcl.demo1.dao.UserDao;
import com.gcl.demo1.entity.TimeLine;
import com.gcl.demo1.service.TimeLineService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author 小关同学
 * @Create 2022/3/19 16:52
 */
@Service
public class TimeLineServiceImpl implements TimeLineService {

    @Autowired
    private TimeLineDao timeLineDao;

    @Autowired
    private UserDao userDao;

    @Override
    public PageInfo<TimeLine> findAllTimeLine(int pageNum, int size){
        PageHelper.startPage(pageNum, size, "public_time desc");
        List<TimeLine> timeLines = timeLineDao.findAllTimeLine();
        for (TimeLine timeLine:timeLines){
            if (timeLine.getContent().length()>25){
                timeLine.setContent(timeLine.getContent().substring(0,25));
            }
            timeLine.setContent(timeLine.getContent().replaceAll("../", "../../"));
        }
        return new PageInfo<>(timeLines);
    }

    @Override
    public List<TimeLine> findAllTimeLine(){
        List<TimeLine> timeLines = timeLineDao.findTimeLineByPublished();
        for (TimeLine timeLine: timeLines){
            timeLine.setUser(userDao.findUserById(timeLine.getUserId()));
        }
        return timeLines;
    }

    @Override
    public TimeLine findTimeLineById(int id){
        return timeLineDao.findTimeLineById(id);
    }

    @Override
    @Transactional
    public void insertTimeLine(TimeLine timeLine){
        timeLineDao.insertTimeLine(timeLine);
    }

    @Override
    @Transactional
    public void updateTimeLineById(TimeLine timeLine){
        timeLineDao.updateTimeLineById(timeLine);
    }

    @Override
    @Transactional
    public void deleteTimeLineById(int id){
        timeLineDao.deleteTimeLineById(id);
    }

}

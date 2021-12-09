package com.gcl.demo1.service.jpa.Impl;

import com.gcl.demo1.dao.jpa.DayCountDao;
import com.gcl.demo1.entity.jpa.DayCount;
import com.gcl.demo1.service.jpa.DayCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/10/5
 */
@Service
public class IDayCountService implements DayCountService {

    @Autowired
    private DayCountDao dayCountDao;

    public List<String> getDates(){
        return dayCountDao.findAllByDay();
    }

    public void saveDayCount(DayCount dayCount){
        dayCountDao.save(dayCount);
    }

    @Override
    public String getDateNewest(){
        return dayCountDao.findAllById(7).getDay();
    }

    @Override
    public List<DayCount> getAll(){
        return dayCountDao.findAll();
    }

    @Override
    public List<Long> getCounts(){
        return dayCountDao.getCounts();
    }

    @Override
    public long getTodayCount(){
        return dayCountDao.findAllById(7).getCount();
    }

}

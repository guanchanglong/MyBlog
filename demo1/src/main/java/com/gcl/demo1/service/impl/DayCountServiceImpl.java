package com.gcl.demo1.service.impl;

import com.gcl.demo1.dao.DayCountDao;
import com.gcl.demo1.entity.DayCount;
import com.gcl.demo1.service.DayCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 小关同学
 * @Create 2022/2/9 9:17
 */
@Service
public class DayCountServiceImpl implements DayCountService {

    @Autowired
    private DayCountDao dayCountDao;

    @Override
    public List<Integer> getCounts(){
        List<DayCount> list = dayCountDao.findAll();
        List<Integer> result = new ArrayList<>(list.size());
        for (DayCount dayCount: list){
            result.add(dayCount.getCount());
        }
        return result;
    }

    @Override
    public List<DayCount> findAll(){
        return dayCountDao.findAll();
    }

    @Override
    @Transactional
    public void updateCountById(DayCount dayCount){
        dayCountDao.updateCountById(dayCount);
    }
}

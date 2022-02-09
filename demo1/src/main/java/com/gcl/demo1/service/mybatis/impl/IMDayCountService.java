package com.gcl.demo1.service.mybatis.impl;

import com.gcl.demo1.dao.mybatis.MDayCountDao;
import com.gcl.demo1.entity.mybatis.DayCount;
import com.gcl.demo1.service.mybatis.MDayCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 小关同学
 * @Create 2022/2/9 9:17
 */
@Service
public class IMDayCountService implements MDayCountService {

    @Autowired
    private MDayCountDao mDayCountDao;

    @Override
    public List<Integer> getCounts(){
        List<DayCount> list = mDayCountDao.getCounts();
        List<Integer> result = new ArrayList<>(list.size());
        for (DayCount dayCount: list){
            result.add(dayCount.getCount());
        }
        return result;
    }
}

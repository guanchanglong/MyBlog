package com.gcl.demo1.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gcl.demo1.dao.DayCountDao;
import com.gcl.demo1.entity.DayCount;
import com.gcl.demo1.service.DayCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author 小关同学
 * @Create 2022/2/9 9:17
 */
@Service
public class DayCountServiceImpl implements DayCountService {

    @Autowired
    private DayCountDao dayCountDao;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public List<Integer> getCounts(){
        List<DayCount> list;
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("findAllDayCount"))){
            list = dayCountDao.findAllDayCount();
            redisTemplate.opsForValue().set("findAllDayCount", JSONObject.toJSONString(list));
        }
        list = JSON.parseArray(redisTemplate.opsForValue().get("findAllDayCount"), DayCount.class);
        assert list != null;
        List<Integer> result = new ArrayList<>(list.size());
        for (DayCount dayCount: list){
            result.add(dayCount.getCount());
        }
        //设置最新的浏览数据信息
        result.set(6, Integer.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get("newestDayCount"))));
        return result;
    }


    @Override
    public void getCountsToUpdateRedis(){
        List<DayCount> list = dayCountDao.findAllDayCount();
        redisTemplate.opsForValue().set("findAllDayCount", JSONObject.toJSONString(list));
    }

    @Override
    public List<DayCount> findAll(){
        return dayCountDao.findAllDayCount();
    }

    @Override
    @Transactional
    public void updateDayCountById(DayCount dayCount){
        dayCountDao.updateDayCountById(dayCount);
    }

    @Override
    @Transactional
    public void updateDayCountOfCountById(int id, int count) {
        dayCountDao.updateDayCountOfCountById(id, count);
    }

    @Override
    public DayCount findDayCountByDay(String day){
        return dayCountDao.findDayCountByDay(day);
    }
}

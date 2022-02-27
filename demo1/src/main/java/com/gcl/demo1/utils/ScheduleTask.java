package com.gcl.demo1.utils;

import com.gcl.demo1.entity.Blog;
import com.gcl.demo1.entity.DayCount;
import com.gcl.demo1.service.BlogService;
import com.gcl.demo1.service.DayCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 小关同学
 * @create 2021/10/5
 */
@Configuration      // 1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduleTask {

    @Autowired
    private DayCountService dayCountService;

    @Autowired
    private RedisDataInnit redisDataInnit;

    @Autowired
    private BlogService blogService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 一天执行一次的任务
     */
    //3.添加定时任务(每天0点执行一次任务)
    @Scheduled(cron = "0 0 0 */1 * ?")
    //或直接指定时间间隔，例如：5秒
//    @Scheduled(fixedRate=30000)
    private void dailyTask() {
        int dayCount = setNewestDayCountToZero();
        changeDayCount(dayCount);
        getAllBlogViewsDataToRedis();
        System.err.println("执行dailyTask任务的时间: " + LocalDateTime.now());
    }

    /**
     * 每次启动时都会运行一次
     * 每2小时更新一次当天的浏览量数据，防止Redis崩溃导致当天的浏览数据丢失
     */
    @Scheduled(fixedDelay = 2*60*60*1000)
    private void updateViewsDataToShow(){

        //更新当天总的浏览数据
        String count = redisTemplate.opsForValue().get("newestDayCount");
        assert count != null;
        dayCountService.updateDayCountOfCountById(7, Integer.valueOf(count));

        //更新每篇博客的浏览数据
        getAllBlogViewsDataToRedis();

        System.err.println("执行updateDayCountToShow任务的时间: " + LocalDateTime.now());
    }

    /**
     * 3小时更新一遍全部的数据
     */
    @Scheduled(fixedDelay = 3*60*60*1000)
    private void updateAllData(){
        redisDataInnit.initData();
        System.err.println("执行updateAllData任务的时间: " + LocalDateTime.now());
    }


    /**
     * 在一天开始的时候更新数据库的浏览数据
     * @param newestDayCount 最新的浏览数据
     */
    private void changeDayCount(int newestDayCount){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        List<DayCount> list = dayCountService.findAll();
        //设置最新的当天浏览量
        list.get(6).setCount(newestDayCount);
        List<DayCount> listToSave = dayCountService.findAll();
        for(int i = 0;i < 7;i++){
            Date date = new Date(System.currentTimeMillis() - i*24*60*60*1000);
            listToSave.get(6-i).setDay(formatter.format(date));
            //今天浏览的
            if (i==0){
                listToSave.get(6-i).setCount(0);
            }else{ //前6天
                listToSave.get(6-i).setCount(list.get(6-i+1).getCount());
            }
            //保存已经修改过了的值
            dayCountService.updateDayCountById(listToSave.get(6-i));
        }
    }

    /**
     * 在Redis中更新当天的总浏览量为0
     * @return 返回当天最后的浏览量
     */
    private int setNewestDayCountToZero(){
        String count = redisTemplate.opsForValue().get("newestDayCount");
        //更新当天的浏览量
        redisTemplate.opsForValue().set("newestDayCount", "0");
        assert count != null;
        return Integer.valueOf(count);
    }


    /**
     * 更新一次数据库中每篇博客的浏览数据
     */
    private void getAllBlogViewsDataToRedis(){
        List<Blog> blogs = blogService.findAll();
        for (Blog blog: blogs){
            int views = blog.getViews();
            //如果原来存在这些键的值的话就更新，不存在的话就初始化
            if (Boolean.TRUE.equals(redisTemplate.hasKey("updateViews:" + blog.getId()))){
                //先获取到值
                views = Integer.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get("updateViews:" + blog.getId())));
                //更新数据库中的博客浏览数据
                blogService.updateBlogOfViewsById(blog.getId(), views);
            }else{
                redisTemplate.opsForValue().set("updateViews:" + blog.getId(), String.valueOf(views));
            }
        }
    }
}

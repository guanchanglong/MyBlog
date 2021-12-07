//package com.gcl.demo1.utils;
//
//import com.gcl.demo1.entity.DayCount;
//import com.gcl.demo1.service.DayCountService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author 小关同学
// * @create 2021/10/5
// */
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
//public class ScheduleTask {
//
//    @Autowired
//    private DayCountService dayCountService;
//
//    //3.添加定时任务(每天0点执行一次任务)
//    @Scheduled(cron = "0 0 0 */1 * ?")
//    //或直接指定时间间隔，例如：5秒
//    //@Scheduled(fixedRate=5000)
//    private void configureTasks() {
//        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
//        List<DayCount> list = dayCountService.getAll();
//        List<DayCount> listToSave = dayCountService.getAll();
//        for(int i = 0;i<7;i++){
//            Date date = new Date(System.currentTimeMillis()-i*24*60*60*1000);
//            //今天浏览的
//            if (i==0){
//                listToSave.get(6-i).setCount(0);
//                listToSave.get(6-i).setDay(formatter.format(date));
//            }else{ //前6天
//                listToSave.get(6-i).setCount(list.get(6-i+1).getCount());
//                listToSave.get(6-i).setDay(formatter.format(date));
//            }
//
//            //保存已经修改过了的值
//            dayCountService.saveDayCount(listToSave.get(6-i));
//        }
//        System.err.println("执行定时任务时间: " + LocalDateTime.now());
//    }
//}

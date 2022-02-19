package com.gcl.demo1.controller;

import com.gcl.demo1.service.DayCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/10/5
 * 统计页面
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private DayCountService dayCountService;

    @GetMapping("/getDateCounts")
    public String archives(Model model) {
        List<Integer> nums = dayCountService.getCounts();
        model.addAttribute("todayCount",nums.get(nums.size()-1));
        //返回一个int类型的数组，长度为7
        model.addAttribute("countList", nums);
        return "statistics";
    }
}

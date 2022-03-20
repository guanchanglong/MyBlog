package com.gcl.demo1.controller;

import com.gcl.demo1.service.TimeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 小关同学
 * @Create 2022/3/19 16:52
 */
@Controller
@RequestMapping("/timeLine")
public class TimeLineShowController {

    @Autowired
    private TimeLineService timeLineService;

    @GetMapping("/timeLinePage")
    public String timeLinePage(Model model){
        model.addAttribute("timeLines", timeLineService.findAllTimeLine());
        return "timeLine";
    }
}

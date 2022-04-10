package com.gcl.demo1.controller;

import com.gcl.demo1.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 小关同学
 * @Create 2022/4/9 17:25
 */
@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/picture")
    public String toPicturePage(Model model){
        model.addAttribute("pictures", pictureService.findAllPictureToSort());
        return "picture";
    }
}

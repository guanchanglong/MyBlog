package com.gcl.demo1.controller;

import com.gcl.demo1.service.jpa.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/16 17:41
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    /**
     * 归档展示
     * @param model
     * @return
     */
    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }
}

package com.gcl.demo1.controller;

import com.gcl.demo1.entity.Tag;
import com.gcl.demo1.service.BlogService;
import com.gcl.demo1.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author：小关同学
 * @date: 2020/12/16 17:43
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags")
    public String tags(@RequestParam(value = "tagId") int tagId,
                       @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                       @RequestParam(value = "size",defaultValue = "8") int size,
                       Model model) {
        List<Tag> tags = tagService.listTagTop(100);
        if (tagId == -1) {
            tagId = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(pageNum, size, tagId, "tag","", null));
        model.addAttribute("activeTagId", tagId);
        return "tags";
    }
}

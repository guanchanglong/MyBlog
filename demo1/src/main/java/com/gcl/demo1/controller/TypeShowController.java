package com.gcl.demo1.controller;

//import com.gcl.demo1.entity.jpa.Type;
import com.gcl.demo1.entity.mybatis.Type;
import com.gcl.demo1.service.jpa.BlogService;
import com.gcl.demo1.service.mybatis.MBlogService;
import com.gcl.demo1.service.mybatis.MTypeService;
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
public class TypeShowController {

    @Autowired
    private MTypeService mTypeService;

    @Autowired
    private MBlogService mBlogService;

    @Autowired
    private BlogService blogService;

    /**
     * 按类型显示博客内容
     * @param pageable
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@RequestParam(value = "typeId") int typeId,
                        @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                        @RequestParam(value = "size",defaultValue = "8") int size,
                        Model model) {
        List<Type> types = mTypeService.listTypeTop(10000);
        if (typeId == -1) {
            typeId = types.get(0).getId();
        }
        model.addAttribute("types", types);
        model.addAttribute("page", mBlogService.listBlog(pageNum,size,typeId,"type"));
        model.addAttribute("activeTypeId", typeId);
        return "types";
    }
}

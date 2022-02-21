package com.gcl.demo1.controller;

import com.gcl.demo1.entity.Type;
import com.gcl.demo1.service.BlogService;
import com.gcl.demo1.service.TypeService;
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
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types")
    public String types(@RequestParam(value = "typeId") int typeId,
                        @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                        @RequestParam(value = "size",defaultValue = "8") int size,
                        Model model) {
        List<Type> types = typeService.listTypeTop(100);
        if (typeId == -1) {
            typeId = types.get(0).getId();
        }
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageNum, size, typeId,"type","", null));
        model.addAttribute("activeTypeId", typeId);
        return "types";
    }
}

package com.gcl.demo1.controller.admin;

import com.gcl.demo1.entity.Type;
import com.gcl.demo1.service.BlogService;
import com.gcl.demo1.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/11 16:05
 */
@Controller
@RequestMapping("/admin/types")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    /**
     * 列表页面入口
     * @param pageNum
     * @param size
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String types(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                        @RequestParam(value = "size",defaultValue = "8")int size,
                        Model model){
        model.addAttribute("page", typeService.listType(pageNum, size));
        return "admin/types";
    }

    /**
     * 新增页面入口
     * @param model
     * @return
     */
    @GetMapping("/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    /**
     * 修改分类标签入口
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}/input")
    public String editInput(@PathVariable int id,
                            Model model){
        model.addAttribute("type", typeService.findTypeById(id));
        return "admin/types-input";
    }

    /**
     * 新增分类
     * @param type
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/add")
    public String post(@Valid Type type,
                       BindingResult result,
                       RedirectAttributes attributes){
        Type type1 = typeService.findTypeByName(type.getName());
        if (type1 != null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        typeService.insertType(type.getName());
        attributes.addFlashAttribute("message","新增成功");
        //返回分类列表
        return "redirect:/admin/types/list";
    }

    /**
     * 修改更新分类操作
     * @param type
     * @param result
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/update/{id}")
    public String editPost(@Valid Type type,
                           BindingResult result,
                           @PathVariable int id,
                           RedirectAttributes attributes){
        Type type1 = typeService.findTypeByName(type.getName());
        if (type1 != null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        typeService.updateTypeById(type.getName(), id);
        attributes.addFlashAttribute("message","更新成功");
        return "redirect:/admin/types/list";
    }

    /**
     * 分类删除操作
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id,
                         RedirectAttributes attributes) {
        if (blogService.findBlogByTypeId(id).size() > 0){
            attributes.addFlashAttribute("message", "删除失败，该类型关联着相应的博客！！！");
        }else{
            typeService.deleteTypeById(id);
            attributes.addFlashAttribute("message", "删除成功");
        }
        return "redirect:/admin/types/list";
    }
}

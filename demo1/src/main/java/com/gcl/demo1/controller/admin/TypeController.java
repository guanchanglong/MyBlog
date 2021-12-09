package com.gcl.demo1.controller.admin;

import com.gcl.demo1.entity.jpa.Type;
import com.gcl.demo1.service.jpa.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
     * 列表页面入口
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/list")
    // @PageableDefault是设置默认分页的相关值的注解，
    // size = 10指定一页放10条数据，sort = {"id"}指定根据主键id来排序，
    // direction = Sort.Direction.DESC来指定按照倒序的方式来排序
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
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
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
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
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());

        if (type1 != null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }

        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        //没保存成功
        if (t == null){
            attributes.addFlashAttribute("message","新增失败");
        }
        //保存成功
        else{
            attributes.addFlashAttribute("message","新增成功");
        }
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
    public String editPost(@Valid Type type,BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
        System.out.println("进入成功");
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type type2 = typeService.updateType(id,type);
        if (type2 == null){
            attributes.addFlashAttribute("message","更新失败");
        }
        else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types/list";
    }

    /**
     * 分类删除操作
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types/list";
    }
}

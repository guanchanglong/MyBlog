package com.gcl.demo1.controller.admin;

import com.gcl.demo1.entity.Tag;
import com.gcl.demo1.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/13 22:28
 */
@Controller
@RequestMapping("/admin/tags")
public class TagController {

    @Autowired
    private TagService tagService;


    /**
     * 列出所有的标签
     * @param pageNum
     * @param size
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String tags(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                       @RequestParam(value = "size",defaultValue = "8")int size,
                       Model model) {
        model.addAttribute("page", tagService.listTag(pageNum,size));
        return "admin/tags";
    }

    /**
     * 给要输入的标签初始化
     * @param model
     * @return
     */
    @GetMapping("/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /**
     * 选择标签
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}/input")
    public String editInput(@PathVariable int id, Model model) {
        model.addAttribute("tag", tagService.findTagById(id));
        return "admin/tags-input";
    }


    /**
     * 添加新的标签
     * @param tag
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/add")
    public String post(@Valid Tag tag,
                       BindingResult result,
                       RedirectAttributes attributes) {
        Tag tag1 = tagService.findTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        tagService.insertTag(tag.getName());
        attributes.addFlashAttribute("message", "新增成功");
        return "redirect:/admin/tags/list";
    }


    /**
     * 更新标签
     * @param tag
     * @param result
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/{id}/update")
    public String editPost(@Valid Tag tag,
                           BindingResult result,
                           @PathVariable int id,
                           RedirectAttributes attributes) {
        Tag tag1 = tagService.findTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        tagService.updateTag(tag.getName(), id);
        attributes.addFlashAttribute("message", "更新成功");
        return "redirect:/admin/tags/list";
    }

    /**
     * 删除标签
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id,
                         RedirectAttributes attributes) {
        if (tagService.findRelationByTagId(id).size() > 0){
            attributes.addFlashAttribute("message", "删除失败，该标签关联着相应的博客！！！");
        }else{
            tagService.deleteTagById(id);
            attributes.addFlashAttribute("message", "删除成功");
        }
        return "redirect:/admin/tags/list";
    }
}

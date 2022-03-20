package com.gcl.demo1.controller.admin;

import com.gcl.demo1.entity.Blog;
import com.gcl.demo1.entity.User;
import com.gcl.demo1.service.BlogService;
import com.gcl.demo1.service.TagService;
import com.gcl.demo1.service.TypeService;
import com.gcl.demo1.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/11 6:51
 */
@Controller
@RequestMapping("/admin/blogs")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs/list";

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/list")
    //以createTime(更新时间)来排序
    public String blogs(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                        @RequestParam(value = "size",defaultValue = "8") int size,
                        Model model) {
        model.addAttribute("types", typeService.listType());
        //获得page对象
        model.addAttribute("page", blogService.listBlogAdmin(pageNum, size,0,"findAll","", null));
        return LIST;
    }

    @PostMapping("/search")
    public String search(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                         @RequestParam(value = "size",defaultValue = "8") int size,
                         BlogQuery blogQuery,
                         Model model) {
        System.out.println(blogQuery.toString());
        model.addAttribute("page", blogService.listBlogAdmin(pageNum, size,0,"searchInAdmin","", blogQuery));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @GetMapping("/{id}/input")
    public String editInput(@PathVariable int id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.findBlogByBlogId(id);
        blog.init();
        model.addAttribute("blog", blog);
        return INPUT;
    }


    /**
     * 提交博客到本地保存
     * @param blog
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/blogsAddOrUpdate")
    public String post(Blog blog,
                       RedirectAttributes attributes,
                       HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.findTypeById(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        if (blog.getId() == 0) {
            blogService.addBlog(blog);
        } else {
            blogService.updateBlog(blog.getId(),blog);
        }
        attributes.addFlashAttribute("message", "操作成功");
        return REDIRECT_LIST;
    }


    /**
     * 删除博客
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }


    /**
     * 返回前3页的推荐博客内容到用户故事专栏
     * @param model
     * @return
     */
    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model) {
        //返回前3篇博客内容到用户故事专栏
        model.addAttribute("newBlogs", blogService.listRecommendBlogTop(3));
        return "admin/_fragments :: newBlogList";
    }
}

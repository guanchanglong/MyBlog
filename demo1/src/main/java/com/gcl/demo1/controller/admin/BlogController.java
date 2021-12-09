package com.gcl.demo1.controller.admin;

import com.gcl.demo1.entity.jpa.Blog;
import com.gcl.demo1.entity.jpa.User;
import com.gcl.demo1.service.jpa.BlogService;
import com.gcl.demo1.service.jpa.TagService;
import com.gcl.demo1.service.jpa.TypeService;
import com.gcl.demo1.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    //以createTime(更新时间)来排序
    public String blogs(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        System.out.println(typeService.listType());
        model.addAttribute("types", typeService.listType());
        //获得page对象
        model.addAttribute("page", blogService.listBlog("admin", pageable, blog));
        return LIST;
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        System.out.println(blogService.listBlog("admin", pageable, blog));
        model.addAttribute("page", blogService.listBlog("admin", pageable, blog));
        return "admin/blogs :: blogList";
    }


    @GetMapping("/input")
    public String input(Model model) {
        setTypeAndTag(model);
        System.out.println(new Blog());
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    private void setTypeAndTag(Model model) {
        System.out.println(typeService.listType());
        System.out.println(tagService.listTag());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }


    @GetMapping("/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        System.out.println(blog);
        model.addAttribute("blog",blog);
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
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        }
        else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        }
        else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }


    /**
     * 删除博客
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }
}

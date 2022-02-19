package com.gcl.demo1.controller;

import com.gcl.demo1.service.BlogService;
import com.gcl.demo1.service.TagService;
import com.gcl.demo1.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author：小关同学
 * @date: 2020/12/16 17:42
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 主页
     * @param pageNum
     * @param size
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                        @RequestParam(value = "size",defaultValue = "8") int size,
                        Model model) {
        model.addAttribute("page", blogService.listBlog(pageNum, size,0,"published","", null));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listBlog(1,8, 0,"published","", null));
        return "index";
    }

    /**
     * 文章查找
     * @param pageNum
     * @param size
     * @param content
     * @param model
     * @return
     */
    @GetMapping("/search")
    public String search(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                         @RequestParam(value = "size",defaultValue = "8") int size,
                         @RequestParam(value = "content") String content,
                         Model model) {
        model.addAttribute("page", blogService.listBlog(pageNum, size,0, "search", content, null));
        model.addAttribute("content", content);
        return "search";
    }

    /**
     * 博客内容显示
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable int id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
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
        return "_fragments :: newBlogList";
    }
}

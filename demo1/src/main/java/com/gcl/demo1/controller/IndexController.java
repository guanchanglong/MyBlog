package com.gcl.demo1.controller;

import com.gcl.demo1.service.mybatis.MBlogService;
import com.gcl.demo1.service.mybatis.MTagService;
import com.gcl.demo1.service.mybatis.MTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/16 17:42
 */
@Controller
public class IndexController {

    @Autowired
    private MBlogService mBlogService;

    @Autowired
    private MTypeService mTypeService;

    @Autowired
    private MTagService mTagService;

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
        model.addAttribute("page",mBlogService.listBlog(pageNum,size));
        model.addAttribute("types", mTypeService.listTypeTop(6));
        model.addAttribute("tags", mTagService.listTagTop(10));
        model.addAttribute("recommendBlogs", mBlogService.listBlog(1,8));
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
        model.addAttribute("page", mBlogService.listBlog(pageNum, size, content));
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
        model.addAttribute("blog", mBlogService.getAndConvert(id));
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
        model.addAttribute("newBlogs", mBlogService.listRecommendBlogTop(3));
        return "_fragments :: newBlogList";
    }
}

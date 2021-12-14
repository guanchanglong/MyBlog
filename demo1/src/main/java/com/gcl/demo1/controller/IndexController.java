package com.gcl.demo1.controller;

import com.gcl.demo1.service.jpa.BlogService;
import com.gcl.demo1.service.mybatis.MBlogService;
import com.gcl.demo1.service.mybatis.MTagService;
import com.gcl.demo1.service.mybatis.MTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/16 17:42
 */
@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;

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
     * @param pageable
     * @param query
     * @param model
     * @return
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
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
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        //返回前3篇博客内容到用户故事专栏
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }
}

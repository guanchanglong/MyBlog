package com.gcl.demo1.controller;

import com.gcl.demo1.entity.Blog;
import com.gcl.demo1.entity.Like;
import com.gcl.demo1.service.BlogService;
import com.gcl.demo1.service.LikeService;
import com.gcl.demo1.service.TagService;
import com.gcl.demo1.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private LikeService likeService;

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
        model.addAttribute("page", blogService.listBlog(pageNum, size,0,"published","",null));
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
    public String blog(@PathVariable int id,
                       HttpServletRequest request,
                       Model model) {
        String ip = getIpAddr(request);
        Blog blog = blogService.getAndConvert(id);
        Like like = likeService.findByBlogIdAndIP(id, ip);
        model.addAttribute("like", like);
        model.addAttribute("blog", blog);
        model.addAttribute("likeCount", blog.getLikeCount());
        model.addAttribute("unLikeCount", blog.getUnLikeCount());
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


    /**
     * 局部刷新点赞、踩数据
     * @param blogId
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/blog/likeData/{blogId}")
    public String likeData(@PathVariable int blogId,
                           HttpServletRequest request,
                           Model model){
        String ip = getIpAddr(request);
        Like like = likeService.findByBlogIdAndIP(blogId, ip);
        int likeCount = likeService.getLikeCount(blogId);
        int unLikeCount = likeService.getUnLikeCount(blogId);
        model.addAttribute("like", like);
        model.addAttribute("likeCount", likeCount);
        model.addAttribute("unLikeCount", unLikeCount);
        return "blog :: likeData";
    }

    /**
     * 变换点赞的状态
     * @param blogId
     * @param flag
     * @param request
     * @return
     */
    @PostMapping("/blog/changeBlogLikeState/{blogId}")
    public String changeBlogLikeState(@PathVariable int blogId,
                                      @RequestParam int flag,
                                      HttpServletRequest request){
        String ip = getIpAddr(request);
        likeService.changeBlogLikeState(blogId, ip, flag);
        return "redirect:/blog/likeData/" + blogId;
    }

    /**
     * 变换点踩的状态
     * @param blogId
     * @param flag
     * @param request
     * @return
     */
    @PostMapping("/blog/changeBlogUnLikeState/{blogId}")
    public String changeBlogUnLikeState(@PathVariable int blogId, @RequestParam int flag, HttpServletRequest request){
        String ip = getIpAddr(request);
        likeService.changeBlogUnLikeState(blogId, ip, flag);
        return "redirect:/blog/likeData/" + blogId;
    }

    /**
     * 获取用户真实ip地址
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {
        //因为使用了Nginx代理，所以这里不能直接获得用户的ip地址，只能获得服务器的ip地址
        //所以，这里不能直接使用request.getRemoteAddr()获取ip地址
        //获取客户端真实ip地址的方法：
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("PRoxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}

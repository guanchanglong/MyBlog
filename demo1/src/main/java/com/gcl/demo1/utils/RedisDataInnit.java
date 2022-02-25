package com.gcl.demo1.utils;

import com.gcl.demo1.entity.Blog;
import com.gcl.demo1.entity.Tag;
import com.gcl.demo1.entity.Type;
import com.gcl.demo1.entity.User;
import com.gcl.demo1.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author 小关同学
 * @Create 2022/2/25 21:25
 * Redis数据初始化类
 */
@Component
public class RedisDataInnit {

    static int size = 8;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private DayCountService dayCountService;

    @Autowired
    private UserService userService;

    @Autowired
    private HobbyService hobbyService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    public void initData(){
        //TagShowController
        //初始化标签页面中所有的标签信息
        List<Tag> tags = tagService.listTagTopToUpdateRedis(100);
        for (Tag tag: tags){
            //如果有博客信息
            if (tag.getBlogs().size() > 0){
                //将分页的每一页的加载进来
                double pageTotal = (double)tag.getBlogs().size()/(double)size;
                for (int pageNum = 1; pageNum <= pageTotal; pageNum++){
                    blogService.listBlogToUpdateRedis(pageNum, size, tag.getId(), "tag");
                }
            }
        }

        //TypeShowController
        //初始化类型页面中所有的类型信息
        List<Type> types = typeService.listTypeTopToUpdateRedis(100);
        for (Type type: types){
            if (type.getBlogs().size() > 0){
                double pageTotal = (double)type.getBlogs().size()/(double)size;
                for (int pageNum = 1; pageNum <= pageTotal; pageNum++){
                    blogService.listBlogToUpdateRedis(pageNum, size, type.getId(),"type");
                }
            }
        }

        //StatisticsController
        //初始化流量统计页面中所有的浏览数据
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        redisTemplate.opsForValue().set("newestDayCount", String.valueOf(dayCountService.findDayCountByDay(formatter.format(new Date())).getCount()));
        dayCountService.getCountsToUpdateRedis();

        //IndexController
        //初始化首页中的所有数据
        PageInfo<Blog> blogPageInfo = blogService.listBlogToUpdateRedis(1, size,0,"published");
        for (Blog blog: blogPageInfo.getList()){
            blogService.getAndConvertToUpdateRedis(blog.getId());
        }
        for (int pageNum = 2; pageNum <= blogPageInfo.getPages(); pageNum++){
            List<Blog> blogs = blogService.listBlogToUpdateRedis(pageNum, size,0,"published").getList();
            for (Blog blog: blogs){
                blogService.getAndConvertToUpdateRedis(blog.getId());
            }
        }
        typeService.listTypeTopToUpdateRedis(6);
        tagService.listTagTopToUpdateRedis(10);
        blogService.listRecommendBlogTopToUpdateRedis(3);

        //ArchiveShowController
        //初始化归档页面的所有数据
        blogService.archiveBlogToUpdateRedis();
        blogService.countBlogToUpdateRedis();

        //AboutShowController
        //初始化关于我页面的所有数据
        User user = userService.getUserByNicknameToUpdateRedis("小关同学");
        tagService.listTagTopToUpdateRedis(10);
        hobbyService.findHobbyByUserIdToUpdateRedis(user.getId());
    }
}

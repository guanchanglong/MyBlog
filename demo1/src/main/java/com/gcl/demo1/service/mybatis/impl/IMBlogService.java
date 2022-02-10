package com.gcl.demo1.service.mybatis.impl;

import com.gcl.demo1.NotFoundException;
import com.gcl.demo1.dao.mybatis.*;
import com.gcl.demo1.entity.mybatis.Blog;
import com.gcl.demo1.entity.mybatis.Tag;
import com.gcl.demo1.entity.mybatis.User;
import com.gcl.demo1.service.mybatis.MBlogService;
import com.gcl.demo1.utils.MarkdownUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小关同学
 * @create 2021/12/9
 */
@Service
public class IMBlogService implements MBlogService {

    @Autowired
    private MBlogDao mBlogDao;

    @Autowired
    private MUserDao mUserDao;

    @Autowired
    private MTypeDao mTypeDao;

    @Autowired
    private MDayCountDao mDayCountDao;

    @Autowired
    private MTagDao mTagDao;

    @Override
    public PageInfo<Blog> listBlog(int pageNum, int size) {
        //按照博客创建时间倒序排序
        PageHelper.startPage(pageNum,size,"create_time desc");
        List<Blog> blogs = mBlogDao.findAllByPublished();
        for (Blog blog:blogs){
            //设置博客的作者
            blog.setUser(mUserDao.findUserById(blog.getUserId()));
            //设置博客的类型
            blog.setType(mTypeDao.findTypeById(blog.getTypeId()));
        }
        return new PageInfo<>(blogs);
    }


    @Override
    public PageInfo<Blog> listBlog(int pageNum, int size, int id, String type) {
        List<Blog> blogs = new ArrayList<>();
        //按照博客创建时间倒序排序
        PageHelper.startPage(pageNum,size,"create_time desc");
        switch (type){
            case "tag":
                blogs = mBlogDao.findBlogByTag(id);
                break;
            case "type":
                blogs = mBlogDao.findBlogByTypeId(id);
                break;
        }
        for (Blog blog:blogs){
            //设置博客的标签信息
            blog.setTags(mTagDao.findTagByBlogId(blog.getId()));
            //设置博客的作者
            blog.setUser(mUserDao.findUserById(blog.getUserId()));
            //设置博客的类型
            blog.setType(mTypeDao.findTypeById(blog.getTypeId()));
        }
        return new PageInfo<>(blogs);
    }

    @Override
    public Blog getAndConvert(int id) {

        //找到对应的博客
        Blog blog = mBlogDao.findBlogById(id);
        //若博客不为空，继续接下来的步骤
        if (blog == null){
            throw new NotFoundException("该博客不存在");
        }
        //找到博客对应的标签信息
        List<Tag> tags = mTagDao.findTagByBlogId(id);
        //找到博客的作者
        User user = mUserDao.findUserById(blog.getUserId());
        //设置博客的标签
        blog.setTags(tags);
        //设置博客的作者
        blog.setUser(user);
        String content = blog.getContent();
        //将博客内容转换为html的格式
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //统计当日浏览次数
        mDayCountDao.updateNewestDay(7);
        //统计该篇博客的浏览次数
        mBlogDao.updateViews(id);
        return blog;
    }

    @Override
    public PageInfo<Blog> listBlog(int pageNum,
                                   int size,
                                   String content){
        PageHelper.startPage(pageNum,size,"create_time desc");
        List<Blog> blogs = mBlogDao.findBlogByContent("%"+content+"%");
        for (Blog blog:blogs){
            //设置博客的作者
            blog.setUser(mUserDao.findUserById(blog.getUserId()));
            //设置博客的类型
            blog.setType(mTypeDao.findTypeById(blog.getTypeId()));
        }
        return new PageInfo<>(blogs);
    }

    /**
     * 返回前几页的推荐博客列表
     * @param size 数目
     * @return 返回博客列表
     */
    @Override
    public List<Blog> listRecommendBlogTop(int size){
        //倒序排序
        PageHelper.startPage(1,size,"create_time desc");
        List<Blog> blogs = mBlogDao.findRecommendBlog();
        return new PageInfo<>(blogs).getList();
    }

    @Override
    public Map<String,List<Blog>> archiveBlog(){
        List<Blog> years = mBlogDao.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for (Blog year:years){
            map.put(year.getYear(),mBlogDao.findByYear(year.getYear()));
        }
        return map;
    }

    @Override
    public int countBlog(){
        return mBlogDao.findAllByPublished().size();
    }
}

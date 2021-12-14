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

import java.util.List;

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
        //按照博客创建时间排序
        PageHelper.startPage(pageNum,size,"create_time");
        List<Blog> list = mBlogDao.findAllByPublished();
        for (Blog blog:list){
            //设置博客的作者
            blog.setUser(mUserDao.findUserById(blog.getUserId()));
            //设置博客的类型
            blog.setType(mTypeDao.findTypeById(blog.getTypeId()));
        }
        return new PageInfo<>(list);
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

}

package com.gcl.demo1.service.mybatis.impl;

import com.gcl.demo1.dao.mybatis.MBlogDao;
import com.gcl.demo1.dao.mybatis.MTypeDao;
import com.gcl.demo1.dao.mybatis.MUserDao;
import com.gcl.demo1.entity.mybatis.Blog;
import com.gcl.demo1.service.mybatis.MBlogService;
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

    @Override
    public PageInfo<Blog> listBlog(int pageNum, int size) {
        //按照博客创建时间排序
        PageHelper.startPage(pageNum,size,"create_time");
        System.out.println(mBlogDao==null);
        List<Blog> list = mBlogDao.findAllByPublished();
        for (Blog blog:list){
            //设置博客的作者
            blog.setUser(mUserDao.findUserById(blog.getUserId()));
            //设置博客的类型
            blog.setType(mTypeDao.findTypeById(blog.getTypeId()));
        }
        return new PageInfo<>(list);
    }

}

package com.gcl.demo1.service.mybatis;


import com.gcl.demo1.entity.mybatis.Blog;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/14 12:30
 */
public interface MBlogService {

    PageInfo<Blog> listBlog(int pageNum, int size);

    PageInfo<Blog> listBlog(int pageNum, int size, int tagId);

    Blog getAndConvert(int id);

    PageInfo<Blog> listBlog(int pageNum, int size, String content);

    List<Blog> listRecommendBlogTop(int size);

    Map<String,List<Blog>> archiveBlog();

    int countBlog();

}

package com.gcl.demo1.service;


import com.gcl.demo1.entity.Blog;
import com.gcl.demo1.vo.BlogQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/14 12:30
 */
public interface BlogService {

    PageInfo<Blog> listBlog(int pageNum, int size, int id, String type, String content, BlogQuery blogQuery);

    Blog getAndConvert(int id);

    List<Blog> listRecommendBlogTop(int size);

    Map<String,List<Blog>> archiveBlog();

    int countBlog();

    Blog findBlogByBlogId(int blogId);

    void addBlog(Blog blog);

    void updateBlog(int blogId,Blog blog);

    void deleteBlog(int blogId);

    List<Blog> findBlogByTypeId(int typeId);

}

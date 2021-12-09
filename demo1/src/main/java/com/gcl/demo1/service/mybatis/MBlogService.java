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
//    Blog getBlog(Long id);
//
//    Blog getAndConvert(Long id);
//
//    PageInfo<Blog> listBlog(String type, Pageable pageable, BlogQuery blog);
//
//    PageInfo<Blog> listBlog(Pageable pageable);
//
//    PageInfo<Blog> listBlog(Long tagId,Pageable pageable);

    PageInfo<Blog> listBlog(int pageNum, int size);

//    List<Blog> listRecommendBlogTop(Integer size);

//    Map<String,List<Blog>> archiveBlog();
//
//    Long countBlog();
//
//    Blog saveBlog(Blog blog);
//
//    Blog updateBlog(Long id,Blog blog);
//
//    void deleteBlog(Long id);
//
//    long allViewCounts();

}

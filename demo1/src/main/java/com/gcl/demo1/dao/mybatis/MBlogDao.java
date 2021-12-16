package com.gcl.demo1.dao.mybatis;

import com.gcl.demo1.entity.mybatis.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/14 12:27
 */
@Mapper
public interface MBlogDao {

    List<Blog> findAllByPublished();

    Blog findBlogById(@Param("blogId") int blogId);

    void updateViews(@Param("blogId") int blogId);

    List<Blog> findBlogByTypeId(@Param("typeId") int typeId);

    List<Blog> findBlogByTag(@Param("tagId") int tagId);

    List<Blog> findBlogByContent(@Param("content") String content);

    List<Blog> findRecommendBlog();

    List<Blog> findGroupYear();

    List<Blog> findByYear(@Param("year") String year);
}

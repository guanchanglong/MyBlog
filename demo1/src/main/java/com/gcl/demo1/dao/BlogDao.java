package com.gcl.demo1.dao;

import com.gcl.demo1.entity.Blog;
import com.gcl.demo1.vo.BlogQuery;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/14 12:27
 */
@Mapper
@Repository
public interface BlogDao {

    Page<Blog> findAllByPublished();

    Blog findBlogById(@Param("blogId") int blogId);

    void incrOneViews(@Param("blogId") int blogId);

    void updateBlogOfViewsById(@Param("blogId") int blogId, @Param("views") int views);

    Page<Blog> findBlogByTypeId(@Param("typeId") int typeId);

    Page<Blog> findBlogByTag(@Param("tagId") int tagId);

    Page<Blog> findBlogByContent(@Param("content") String content);

    List<Blog> findRecommendBlog();

    List<Blog> findGroupYear();

    List<Blog> findByYear(@Param("year") String year);

    Page<Blog> findAll();

    void insertBlog(@Param("blog") Blog blog);

    void updateBlog(@Param("blog") Blog blog);

    void deleteBlogById(@Param("blogId") int blogId);

    Page<Blog> findBlogByBlogQuery(@Param("blogQuery") BlogQuery blogQuery);

    List<Blog> findBlogByTypeIdByAdmin(@Param("typeId") int typeId);

    void updateBlogOfLikeCountById(@Param("blogId") int blogId, @Param("likeCount") int likeCount);

    void updateBlogOfUnLikeCountById(@Param("blogId") int blogId, @Param("unLikeCount") int unLikeCount);
}

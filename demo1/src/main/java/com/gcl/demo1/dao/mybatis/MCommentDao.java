package com.gcl.demo1.dao.mybatis;

import com.gcl.demo1.entity.mybatis.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/16 17:13
 */
@Mapper
public interface MCommentDao {

    List<Comment> findCommentByBlogId(@Param("blogId") int blogId);

    Comment findCommentById(@Param("id") int id);

}

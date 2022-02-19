package com.gcl.demo1.dao;

import com.gcl.demo1.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author：小关同学
 * @date: 2020/12/16 17:13
 */
@Mapper
public interface CommentDao {

    List<Comment> findCommentByBlogId(@Param("blogId") int blogId);

//    Comment findCommentById(@Param("id") int id);

    List<Comment> findChildCommentById(@Param("fatherCommentId") int fatherCommentId);

    void insertComment(@Param("comment") Comment comment);

    void saveCommentRelation(@Param("comment") Comment comment);

    Comment findCommentByCreateTime(@Param("createTime") Date createTime);

    List<Comment> findAllMessageComment();

    void deleteCommentByBlogId(@Param("blogId") int blogId);

}

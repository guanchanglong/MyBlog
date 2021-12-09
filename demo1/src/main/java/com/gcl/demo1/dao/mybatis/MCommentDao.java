package com.gcl.demo1.dao.mybatis;

import com.gcl.demo1.entity.jpa.Comment;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/16 17:13
 */
public interface MCommentDao {

    Comment findAllById(Long id);

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}

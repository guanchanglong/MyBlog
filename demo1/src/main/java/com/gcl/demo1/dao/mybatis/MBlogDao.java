package com.gcl.demo1.dao.mybatis;

import com.gcl.demo1.entity.mybatis.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/14 12:27
 */
@Mapper
public interface MBlogDao {

    List<Blog> findAllByPublished();
}

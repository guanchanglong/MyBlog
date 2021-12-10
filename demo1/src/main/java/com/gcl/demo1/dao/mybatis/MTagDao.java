package com.gcl.demo1.dao.mybatis;

import com.gcl.demo1.entity.mybatis.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/13 22:20
 */
@Mapper
public interface MTagDao {

    List<Tag> findTagByBlogId(@Param("blogId") int blogId);

    Tag findTagById(int id);

    Tag findAllByName(String name);

    List<Tag> findTop(Pageable pageable);
}

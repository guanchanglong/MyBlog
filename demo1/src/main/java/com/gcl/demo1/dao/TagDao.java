package com.gcl.demo1.dao;

import com.gcl.demo1.entity.BlogAndTag;
import com.gcl.demo1.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/13 22:20
 */
@Mapper
@Repository
public interface TagDao {

    List<Tag> findTagByBlogId(@Param("blogId") int blogId);

    List<Tag> findAllTag();

    Tag findAllById(@Param("tagId") int tagId);

    void saveTagRelation(@Param("blogId") int blogId,@Param("tagId") int tagId);

    List<BlogAndTag> findTagIdByBlogId(@Param("blogId") int blogId);

    void updateTagRelationById(@Param("newTagId") int newTagId,@Param("relationId") int relationId);

    void deleteTagRelationById(@Param("id") int id);

    void deleteTagRelationByBlogId(@Param("blogId") int blogId);

    Tag findTagByName(@Param("name") String name);

    void insertTag(@Param("name") String name);

    void updateTag(@Param("name") String name, @Param("id") int id);

    void deleteTagById(@Param("id") int id);

    List<BlogAndTag> findRelationByTagId(@Param("tagId") int tagId);
}

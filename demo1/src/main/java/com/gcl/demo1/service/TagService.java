package com.gcl.demo1.service;

import com.gcl.demo1.entity.BlogAndTag;
import com.gcl.demo1.entity.Tag;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/13
 */
public interface TagService {

    List<Tag> listTagTop(int size);

    List<Tag> listTagTopToUpdateRedis(int size);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    PageInfo<Tag> listTag(int pageNum, int size);

    Tag findTagById(int tagId);

    Tag findTagByName(String tagName);

    void insertTag(String tagName);

    void updateTag(String tagName,int tagId);

    void deleteTagById(int id);

    List<BlogAndTag> findRelationByTagId(int tagId);
}

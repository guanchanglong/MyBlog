package com.gcl.demo1.service;

import com.gcl.demo1.entity.Tag;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/13 22:20
 */
public interface TagService {
    Tag saveTag(Tag type);

    Tag getTag(Long id);

    Tag getTagByName(String name);

//    PageInfo<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);

    List<Tag> listTag(String ids);

    Tag updateTag(Long id, Tag type);

    void deleteTag(Long id);
}

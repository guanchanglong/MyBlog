package com.gcl.demo1.service.mybatis;

import com.gcl.demo1.entity.mybatis.Tag;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/13
 */
public interface MTagService {

    List<Tag> listTagTop(int size);
}

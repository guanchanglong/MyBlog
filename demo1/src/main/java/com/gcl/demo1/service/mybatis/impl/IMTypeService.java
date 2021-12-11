package com.gcl.demo1.service.mybatis.impl;

import com.gcl.demo1.dao.mybatis.MBlogDao;
import com.gcl.demo1.dao.mybatis.MTypeDao;
import com.gcl.demo1.entity.mybatis.Blog;
import com.gcl.demo1.entity.mybatis.Type;
import com.gcl.demo1.service.mybatis.MTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 小关同学
 * @create 2021/12/11
 */
@Service
public class IMTypeService implements MTypeService {

    @Autowired
    private MTypeDao mTypeDao;

    @Autowired
    private MBlogDao mBlogDao;

    @Override
    public List<Type> listTypeTop(int size){
        List<Type> result = new ArrayList<>();
        List<Type> list = mTypeDao.findAllType();
        for (Type type:list){
            List<Blog> blogs = mBlogDao.findBlogByTypeId(type.getId());
            type.setBlogs(blogs);
        }
        //排序
        Collections.sort(list);

        for (Type type:list){
            if (size!=0){
                result.add(type);
            }else{
                break;
            }
            size--;
        }
        return result;
    }
}

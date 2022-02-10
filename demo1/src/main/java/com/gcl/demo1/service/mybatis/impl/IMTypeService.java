package com.gcl.demo1.service.mybatis.impl;

import com.gcl.demo1.dao.mybatis.MBlogDao;
import com.gcl.demo1.dao.mybatis.MTypeDao;
import com.gcl.demo1.entity.mybatis.Type;
import com.gcl.demo1.service.mybatis.MTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
        List<Type> types = mTypeDao.findAllType();
        //排序
        Collections.sort(types);
        for (Type type:types){
            type.setBlogs(mBlogDao.findBlogByTypeId(type.getId()));
            if (size==0){
                break;
            }
            size--;
        }
        return types;
    }
}

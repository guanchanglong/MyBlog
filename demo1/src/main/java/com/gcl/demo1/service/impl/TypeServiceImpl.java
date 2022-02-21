package com.gcl.demo1.service.impl;

import com.gcl.demo1.dao.BlogDao;
import com.gcl.demo1.dao.TypeDao;
import com.gcl.demo1.entity.Type;
import com.gcl.demo1.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/11
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private BlogDao blogDao;

    @Override
    public List<Type> listTypeTop(int size){
        List<Type> types = typeDao.findAllType();
        List<Type> result = new ArrayList<>(size);
        for (Type type:types){
            type.setBlogs(blogDao.findBlogByTypeId(type.getId()));
        }
        //排序
        Collections.sort(types);
        for (Type type:types){
            if (size==0){
                break;
            }
            result.add(type);
            size--;
        }
        return result;
    }

    @Override
    public List<Type> listType(){
        return typeDao.findAllType();
    }

    @Override
    public Type findTypeById(int id){
        return typeDao.findTypeById(id);
    }

    @Override
    public PageInfo<Type> listType(int pageNum, int size){
        PageHelper.startPage(pageNum,size);
        List<Type> types = typeDao.findAllType();
        return new PageInfo<>(types);
    }

    @Override
    public Type findTypeByName(String name){
        return typeDao.findAllByName(name);
    }

    @Override
    @Transactional
    public void insertType(String typeName){
        typeDao.insertType(typeName);
    }

    @Override
    @Transactional
    public void updateTypeById(String typeName, int typeId){
        typeDao.updateTypeById(typeName, typeId);
    }

    @Override
    @Transactional
    public void deleteTypeById(int typeId){
        typeDao.deleteTypeById(typeId);
    }
}

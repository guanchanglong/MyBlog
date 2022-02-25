package com.gcl.demo1.service;


import com.gcl.demo1.entity.Type;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/11 15:51
 */
public interface TypeService {

    List<Type> listTypeTop(int size);

    List<Type> listTypeTopToUpdateRedis(int size);

    List<Type> listType();

    Type findTypeById(int id);

    PageInfo<Type> listType(int pageNum, int size);

    Type findTypeByName(String name);

    void insertType(String typeName);

    void updateTypeById(String typeName, int typeId);

    void deleteTypeById(int typeId);

}

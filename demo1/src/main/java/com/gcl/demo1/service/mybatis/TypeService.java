package com.gcl.demo1.service.mybatis;


import com.gcl.demo1.entity.jpa.Type;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/11 15:51
 */
public interface TypeService {
    Type saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

//    PageInfo<Type> listType(Pageable pageable);

    Type updateType(Long id, Type type);

    void deleteType(Long id);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);

}

package com.gcl.demo1.dao;

import com.gcl.demo1.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/11 15:54
 */
@Mapper
public interface TypeDao {
    Type findTypeById(@Param("typeId") int id);

    List<Type> findAllType();

    Type findAllByName(@Param("name") String name);

    void insertType(@Param("name") String name);

    void updateTypeById(@Param("name") String name,@Param("id") int id);

    void deleteTypeById(@Param("id") int id);
}

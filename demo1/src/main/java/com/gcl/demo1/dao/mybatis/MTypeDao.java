package com.gcl.demo1.dao.mybatis;

import com.gcl.demo1.entity.mybatis.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/11 15:54
 */
@Mapper
public interface MTypeDao {
    Type findTypeById(@Param("typeId") int id);

    List<Type> findAllType();

    Type findAllByName(String name);

    List<Type> findTop(Pageable pageable);
}

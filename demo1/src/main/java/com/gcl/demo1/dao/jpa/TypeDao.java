package com.gcl.demo1.dao.jpa;

import com.gcl.demo1.entity.jpa.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/11 15:54
 */
public interface TypeDao extends JpaRepository<Type,Long> {
    Type findAllById(Long id);

    Type findAllByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}

package com.gcl.demo1.dao.jpa;

import com.gcl.demo1.entity.jpa.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/13 22:20
 */
public interface TagDao extends JpaRepository<Tag,Long> {
    Tag findAllById(Long id);

    Tag findAllByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}

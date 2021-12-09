package com.gcl.demo1.dao.mybatis;

import com.gcl.demo1.entity.jpa.Tag;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/13 22:20
 */
public interface MTagDao {
    Tag findAllById(Long id);

    Tag findAllByName(String name);

    List<Tag> findTop(Pageable pageable);
}

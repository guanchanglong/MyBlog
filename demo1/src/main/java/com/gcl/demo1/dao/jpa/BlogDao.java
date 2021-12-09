package com.gcl.demo1.dao.jpa;

import com.gcl.demo1.entity.jpa.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/14 12:27
 */
//JpaSpecificationExecutor是JPA里面的一个接口，这个接口可以帮我们实现一些动态的组合查询
public interface BlogDao extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.published = 1")
    Page<Blog> findAllByPublished(Pageable pageable);

    @Query("select b from Blog b where b.published = 1")
    Page<Blog> findAllByPublished(Specification<Blog> blogSpecification, Pageable pageable);

    Blog findAllById(Long id);

    @Query("select b from Blog b where b.published = 1 and b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.published = 1 and (b.title like ?1 or b.content like ?1)")
    Page<Blog> findByQuery(String query, Pageable pageable);

    Long countByPublished(boolean isPublished);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    //select function('date_format',b.createTime,'%Y') as year from Blog b group by function('date_format',b.createTime,'%Y') order by year desc
//    @Query("select function('date_format',b.createTime,'%Y') as year from Blog b group by function('date_format',b.createTime,'%Y') order by year desc")
//    List<String> findGroupYear();

//    @Query("select b from Blog b where b.published = 1 and function('date_format',b.createTime,'%Y') = ?1")
//    List<Blog> findByYear(String year);

    @Query("select sum(b.views) from Blog b")
    long countAllViews();
}

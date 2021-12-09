package com.gcl.demo1.dao.jpa;

import com.gcl.demo1.entity.jpa.DayCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/10/5
 */
public interface DayCountDao extends JpaRepository<DayCount,Long> {

    @Query("select day from DayCount")
    List<String> findAllByDay();

    DayCount findAllById(long id);

    /**
     * 更新当天的数据
     * @param id
     */
    @Transactional
    @Modifying
    @Query("update DayCount d set d.count = d.count+1 where d.id = ?1")
    void updateNewestDay(Long id);

    @Query("select d.count from DayCount d")
    List<Long> getCounts();


}

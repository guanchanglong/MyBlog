package com.gcl.demo1.dao;

import com.gcl.demo1.entity.Hobby;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/16
 */
@Mapper
@Repository
public interface HobbyDao {

    List<Hobby> findHobbyByUserId(@Param("userId") int userId);

}

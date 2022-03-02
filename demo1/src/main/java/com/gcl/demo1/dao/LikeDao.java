package com.gcl.demo1.dao;

import com.gcl.demo1.entity.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author 小关同学
 * @Create 2022/3/1 9:23
 */
@Mapper
@Repository
public interface LikeDao {

    Like findLikeAndUnLikeByBlogIdAndIP(@Param("blogId") int blogId, @Param("viewerIP") String viewerIP);
}

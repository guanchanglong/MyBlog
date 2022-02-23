package com.gcl.demo1.dao;

import com.gcl.demo1.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/10 21:11
 */
@Mapper
@Repository
public interface UserDao {

    User findUserById(@Param("userId") int userId);

    User findUserByNickname(@Param("nickname") String nickname);

    User login(String username,String password);
}

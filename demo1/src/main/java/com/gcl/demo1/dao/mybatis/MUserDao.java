package com.gcl.demo1.dao.mybatis;

import com.gcl.demo1.entity.mybatis.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/10 21:11
 */
@Mapper
public interface MUserDao {

    User findUserById(@Param("userId") int userId);

    User findByUsernameAndPassword(String username,String password);

    User findAllByUsername(String name);
}

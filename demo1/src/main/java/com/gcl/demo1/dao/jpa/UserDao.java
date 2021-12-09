package com.gcl.demo1.dao.jpa;

import com.gcl.demo1.entity.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/10 21:11
 */
//JpaRepository<User, Integer>里面的User代表你jpa操作的对象是User,Long则是代表主键的类型
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username,String password);

    User findAllByUsername(String name);
}

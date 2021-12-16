package com.gcl.demo1.service.mybatis;

import com.gcl.demo1.entity.mybatis.User;

/**
 * @author 小关同学
 * @create 2021/12/16
 */
public interface MUserService {

    User getUser(String username);

}

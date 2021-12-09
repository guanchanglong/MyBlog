package com.gcl.demo1.dao.jpa;

import com.gcl.demo1.entity.jpa.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 小关同学
 * @create 2021/10/3
 */
public interface MessageDao extends JpaRepository<Message,Long>,JpaSpecificationExecutor<Message> {
}

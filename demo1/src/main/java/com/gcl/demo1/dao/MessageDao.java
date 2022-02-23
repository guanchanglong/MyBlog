package com.gcl.demo1.dao;

import com.gcl.demo1.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/10/3
 */
@Mapper
@Repository
public interface MessageDao {
    List<Message> findAllMessage();

    List<Message> findChildMessageById(@Param("fatherMessageId") int fatherMessageId);

    void insertMessage(@Param("message") Message message);

//    Message findMessageByCreateTime(@Param("createTime") Date createTime);

    void saveMessageRelation(@Param("message") Message message);
}

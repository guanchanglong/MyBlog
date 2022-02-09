package com.gcl.demo1.dao.mybatis;

import com.gcl.demo1.entity.mybatis.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/10/3
 */
@Mapper
public interface MMessageDao {
    List<Message> findAllMessage();

    List<Message> findChildMessageById(@Param("fatherMessageId") int fatherMessageId);

    void insertMessage(@Param("message") Message message);

    Message findMessageByCreateTime(@Param("createTime") Date createTime);

    void saveMessageRelation(@Param("message") Message message);
}

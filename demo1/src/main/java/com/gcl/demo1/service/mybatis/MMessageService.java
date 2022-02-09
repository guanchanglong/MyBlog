package com.gcl.demo1.service.mybatis;

import com.gcl.demo1.entity.mybatis.Message;
import com.github.pagehelper.PageInfo;

/**
 * @Author 小关同学
 * @Create 2022/2/8 10:57
 */
public interface MMessageService {
    PageInfo<Message> listMessage(int pageNum, int size);

    void saveMessage(Message message);
}

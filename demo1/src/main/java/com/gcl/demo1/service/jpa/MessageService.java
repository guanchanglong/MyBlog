package com.gcl.demo1.service.jpa;

import com.gcl.demo1.entity.jpa.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author 小关同学
 * @create 2021/10/3
 */
public interface MessageService {
    Message saveMessage(Message message);

    Page<Message> listMessage(Pageable pageable);

    void findParentMessage(Message message);
}

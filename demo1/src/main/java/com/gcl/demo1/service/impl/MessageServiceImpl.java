package com.gcl.demo1.service.impl;

import com.gcl.demo1.dao.MessageDao;
import com.gcl.demo1.entity.Message;
import com.gcl.demo1.service.MessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @Author 小关同学
 * @Create 2022/2/8 10:56
 */
@Service
public class MessageServiceImpl implements MessageService {


    @Autowired
    private MessageDao messageDao;

    /**
     * 递归设置评论关系
     * @param father
     * @param original
     * @return
     */
    private List<Message> findChildMessage(Message father,List<Message> original){
        List<Message> messages = messageDao.findChildMessageById(father.getId());
        if (messages.size()!=0){
            for (Message message:messages){
                findChildMessage(message,original);
                message.setParentMessage(father);
                original.add(message);
            }
        }
        Collections.sort(original);
        return original;
    }


    /**
     * 留言板显示留言信息
     * @param pageNum
     * @param size
     * @return
     */
    @Override
    public PageInfo<Message> listMessage(int pageNum, int size) {
        PageHelper.startPage(pageNum,size,"create_time desc");
        List<Message> messages = messageDao.findAllMessage();
        Iterator<Message> iterable = messages.iterator();
        for (int i = messages.size()-1;i>=0;i--){
            Message message = messages.get(i);
            if (message.getParentMessageId()==-1){
                message.setReplyMessages(findChildMessage(message,new ArrayList<Message>()));
            }
        }
        //List的集合框架中只有使用Iterator才能实现边遍历边删除元素
        while(iterable.hasNext()){
            Message message = iterable.next();
            if (message.getParentMessageId()!=-1){
                iterable.remove();
            }
        }
        return new PageInfo<>(messages);
    }

    /**
     * 保存留言信息
     * @param message
     * @return
     */
    @Override
    @Transactional
    public void saveMessage(Message message) {
        messageDao.insertMessage(message);
        //没解决如何在插入数据之后获取插入数据主键
//        message.setId(messageDao.findMessageByCreateTime(message.getCreateTime()).getId());
        if (message.getParentMessageId()!=-1){
            System.out.println("新增的留言的主键：" + message.getId());
            messageDao.saveMessageRelation(message);
        }
    }
}

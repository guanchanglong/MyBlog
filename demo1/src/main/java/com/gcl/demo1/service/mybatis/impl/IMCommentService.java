package com.gcl.demo1.service.mybatis.impl;

import com.gcl.demo1.dao.mybatis.MCommentDao;
import com.gcl.demo1.entity.mybatis.Comment;
import com.gcl.demo1.service.mybatis.MCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/16
 */
@Service
public class IMCommentService implements MCommentService {

    @Autowired
    private MCommentDao mCommentDao;

    /**
     * 返回博客评论列表
     * @param blogId
     * @return
     */
    @Override
    public List<Comment> listCommentByBlogId(int blogId){
        List<Comment> comments = mCommentDao.findCommentByBlogId(blogId);
        Iterator<Comment> iterable = comments.iterator();
        while(iterable.hasNext()){
            Comment comment = iterable.next();
            if (comment.getParentCommentId()!=0){
                iterable.remove();
            }else{
                comment.setReplyComments(findChildComment(comment,new ArrayList<Comment>()));
            }
        }
        return comments;
    }

    /**
     * 递归设置评论关系
     * @param father
     * @param original
     * @return
     */
    public List<Comment> findChildComment(Comment father,List<Comment> original){
        List<Comment> comments = mCommentDao.findChildCommentById(father.getId());
        if (comments.size()!=0){
            for (Comment comment:comments){
                findChildComment(comment,original);
                comment.setParentComment(father);
                original.add(comment);
            }
        }
        Collections.sort(original);
        return original;
    }
}

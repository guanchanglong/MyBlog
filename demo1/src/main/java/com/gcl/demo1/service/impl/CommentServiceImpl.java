package com.gcl.demo1.service.impl;

import com.gcl.demo1.dao.CommentDao;
import com.gcl.demo1.entity.Comment;
import com.gcl.demo1.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 小关同学
 * @create 2021/12/16
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    /**
     * 返回博客评论列表
     * @param blogId
     * @return
     */
    @Override
    public List<Comment> listCommentByBlogId(int blogId){
        List<Comment> comments = commentDao.findCommentByBlogId(blogId);
        Iterator<Comment> iterable = comments.iterator();
        while(iterable.hasNext()){
            Comment comment = iterable.next();
            if (comment.getParentCommentId()!=-1){
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
    private List<Comment> findChildComment(Comment father,List<Comment> original){
        List<Comment> comments = commentDao.findChildCommentById(father.getId());
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


    /**
     * 插入评论
     * @param comment 新增的评论
     */
    @Override
    @Transactional
    public void insertComment(Comment comment){
        commentDao.insertComment(comment);
        //设置插入后的主键
        comment.setId(commentDao.findCommentByCreateTime(comment.getCreateTime()).getId());
        if (comment.getParentCommentId()!=-1){
            commentDao.saveCommentRelation(comment);
        }
    }


    /**
     * 留言板显示留言信息
     * @param pageNum
     * @param size
     * @return
     */
    @Override
    public PageInfo<Comment> listMessage(int pageNum, int size) {
        PageHelper.startPage(pageNum,size,"create_time");
        List<Comment> comments = commentDao.findAllMessageComment();
        List<Comment> result = new ArrayList<>(comments.size());
        Iterator<Comment> iterable = comments.iterator();
        while(iterable.hasNext()){
            Comment comment = iterable.next();
            if (comment.getParentCommentId()!=-1){
                iterable.remove();
            }else{
                comment.setReplyComments(findChildComment(comment,new ArrayList<Comment>()));
            }
        }
        return new PageInfo<>(result);
    }
}

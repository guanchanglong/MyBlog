package com.gcl.demo1.service;

import com.gcl.demo1.entity.Like;

/**
 * @Author 小关同学
 * @Create 2022/3/1 9:28
 */
public interface LikeService {

    Like findByBlogIdAndIP(int blogId, String viewerIP);

    void changeBlogLikeState(int blogId, String ip, int flag);

    void changeBlogUnLikeState(int blogId, String ip, int flag);

    int getLikeCount(int blogId);

    int getUnLikeCount(int blogId);

}

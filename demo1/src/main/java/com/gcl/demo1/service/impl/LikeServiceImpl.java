package com.gcl.demo1.service.impl;

import com.gcl.demo1.dao.LikeDao;
import com.gcl.demo1.entity.Like;
import com.gcl.demo1.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author 小关同学
 * @Create 2022/3/1 9:28
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeDao likeDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取点赞、踩数据
     * @param blogId
     * @param viewerIP
     * @return
     */
    @Override
    public Like findByBlogIdAndIP(int blogId, String viewerIP){
        Like like;
        Like result = new Like();
        System.out.println("访问的BlogId："+blogId+", 访问者的IP地址：" + viewerIP);
        if (!redisTemplate.opsForHash().hasKey("blogLikeList:" + blogId, viewerIP)){
            like = likeDao.findLikeAndUnLikeByBlogIdAndIP(blogId, viewerIP);
            if (like == null){
                redisTemplate.opsForHash().put("blogLikeList:" + blogId, viewerIP, "0");
            }else{
                if (like.isLike()){
                    redisTemplate.opsForHash().put("blogLikeList:" + blogId, viewerIP, "1");
                }
                if (like.isUnLike()){
                    redisTemplate.opsForHash().put("blogLikeList:" + blogId, viewerIP, "-1");
                }
            }
        }

        int flag = Integer.valueOf((String) Objects.requireNonNull(redisTemplate.opsForHash().get("blogLikeList:" + blogId, viewerIP)));

        if (flag == 0){
            result = new Like(blogId, viewerIP, false, false);
        }
        if (flag == 1){
            result = new Like(blogId, viewerIP, true, false);
        }
        if (flag == -1){
            result = new Like(blogId, viewerIP, false, true);
        }
        System.out.println("点赞状态blogLikeFlag：" + result.isLike());
        return result;
    }

    /**
     * 增加博客点赞数
     * @param blogId
     */
    private void addLikeCount(int blogId){
        //自增操作
        RedisAtomicLong entityIdCounter = new RedisAtomicLong("blogLikeCount:" + blogId, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        entityIdCounter.getAndIncrement();
    }

    /**
     * 减少博客点赞数
     * @param blogId
     */
    private void deleteLikeCount(int blogId){
        //自减操作
        RedisAtomicLong entityIdCounter = new RedisAtomicLong("blogLikeCount:" + blogId, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        entityIdCounter.getAndDecrement();
    }

    /**
     * 增加博客点踩数
     * @param blogId
     */
    private void addUnLikeCount(int blogId){
        //自增操作
        RedisAtomicLong entityIdCounter = new RedisAtomicLong("blogUnLikeCount:" + blogId, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        entityIdCounter.getAndIncrement();
    }

    /**
     * 减少博客点踩数据
     * @param blogId
     */
    private void deleteUnLikeCount(int blogId){
        //自减操作
        RedisAtomicLong entityIdCounter = new RedisAtomicLong("blogUnLikeCount:" + blogId, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        entityIdCounter.getAndDecrement();
    }

    /**
     * 改变博客点赞状态
     * @param blogId
     * @param ip
     * @param flag
     */
    @Override
    public void changeBlogLikeState(int blogId, String ip, int flag){
        int redisFlag = Integer.valueOf((String) Objects.requireNonNull(redisTemplate.opsForHash().get("blogLikeList:" + blogId, ip)));

        //点赞
        //更新该博客下的该地址访问的访客点赞状态为点赞
        //并且增加点赞数
        if (flag == 1){
            redisTemplate.opsForHash().put("blogLikeList:" + blogId, ip, "1");
            //如果本来的用户点赞状态是点踩的状态
            //减少点踩数
            if (redisFlag == -1){
                deleteUnLikeCount(blogId);
            }
            addLikeCount(blogId);
        }

        //取消点赞
        //更新该博客下的该地址访问的访客点赞状态为不点赞
        if (flag == -1){
            redisTemplate.opsForHash().put("blogLikeList:" + blogId, ip, "0");

            //如果本来的用户点赞状态是点赞的状态
            //减少点赞数
            if (redisFlag == 1){
                deleteLikeCount(blogId);
            }
        }
    }

    /**
     * 改变博客点踩状态
     * @param blogId
     * @param ip
     * @param flag
     */
    @Override
    public void changeBlogUnLikeState(int blogId, String ip, int flag){
        int redisFlag = Integer.valueOf((String) Objects.requireNonNull(redisTemplate.opsForHash().get("blogLikeList:" + blogId, ip)));

        //点踩
        //更新该博客下的该地址访问的访客点赞状态为点踩
        //并且增加点踩数
        if (flag == 1){
            redisTemplate.opsForHash().put("blogLikeList:" + blogId, ip, "-1");
            //如果本来的用户点赞状态是点赞的状态
            //减少点赞数
            if (redisFlag == 1){
                deleteLikeCount(blogId);
            }
            addUnLikeCount(blogId);
        }

        //取消点踩
        //更新该博客下的该地址访问的访客点赞状态为不点踩
        if (flag == -1){
            redisTemplate.opsForHash().put("blogLikeList:" + blogId, ip, "0");

            //如果本来的用户点踩状态是点踩的状态
            //减少点踩数
            if (redisFlag == -1){
                deleteUnLikeCount(blogId);
            }
        }
    }

    /**
     * 获取博客的点赞数
     * @param blogId
     * @return
     */
    @Override
    public int getLikeCount(int blogId){
        return Integer.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get("blogLikeCount:" + blogId)));
    }

    /**
     * 获取博客的点踩数
     * @param blogId
     * @return
     */
    @Override
    public int getUnLikeCount(int blogId){
        return Integer.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get("blogUnLikeCount:" + blogId)));
    }
}

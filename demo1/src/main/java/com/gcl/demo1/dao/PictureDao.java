package com.gcl.demo1.dao;

import com.gcl.demo1.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author 小关同学
 * @Create 2022/4/10 9:52
 */
@Mapper
@Repository
public interface PictureDao {

    List<Picture> findAllPicture();

    List<Picture> findAllPictureToSort();

    void addPicture(@Param("picture") Picture picture);

    void updatePicture(@Param("picture") Picture picture);

    void deletePictureById(@Param("id") int id);

    Picture findPictureById(@Param("id") int id);
}

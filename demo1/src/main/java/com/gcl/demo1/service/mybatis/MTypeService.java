package com.gcl.demo1.service.mybatis;


import com.gcl.demo1.entity.mybatis.Type;

import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/11 15:51
 */
public interface MTypeService {

    List<Type> listTypeTop(int size);

}

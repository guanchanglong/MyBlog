package com.gcl.demo1.service.jpa.Impl;

import com.gcl.demo1.NotFoundException;
import com.gcl.demo1.dao.jpa.TypeDao;
import com.gcl.demo1.entity.jpa.Type;
import com.gcl.demo1.service.jpa.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/11 15:53
 */
@Service
public class ITypeService implements TypeService {

    @Autowired
    private TypeDao typeDao;

    //放在事务里面（增删改查等操作都放在事务里面）
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeDao.save(type);
    }

    //查询操作
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeDao.findAllById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.findAllByName(name);
    }

    //分页查询
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeDao.findAll(pageable);
    }

    //更新操作
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeDao.findAllById(id);
        if (t == null){
            throw new NotFoundException("不存在该类型");
        }
        //如果存在，就将type的值赋给我们查到的类型
        BeanUtils.copyProperties(type,t);
        return typeDao.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeDao.deleteById(id);
    }

    @Override
    public List<Type> listType() {
        return typeDao.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        List<Type> list =  typeDao.findTop(pageable);
        for(Type type:list){
            //如果是草稿的话，就把它从集合中删除
            type.getBlogs().removeIf(blog -> !blog.isPublished());
        }
        return list;
    }
}

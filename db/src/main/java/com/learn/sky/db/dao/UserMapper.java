package com.learn.sky.db.dao;

import com.learn.sky.db.entity.UserEntity;

import java.util.List;

/**
 * @Author: JiuBuKong
 * @Date: 2018/11/2 上午11:43
 */
public interface UserMapper {
    List<UserEntity> getAll();

    void insert(UserEntity user);
}

package com.learn.sky.shard.dao;


import com.learn.sky.shard.entity.UserEntity;

import java.util.List;

/**
 * @Author: JiuBuKong
 * @Date: 2018/11/2 上午11:43
 */
public interface UserMapper {
    List<UserEntity> getAll();

    void insert(UserEntity user);
}

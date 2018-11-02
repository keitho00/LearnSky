package com.learn.sky.db;

import com.learn.sky.db.dao.UserMapper;
import com.learn.sky.db.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: JiuBuKong
 * @Date: 2018/11/2 下午2:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {


    @Resource
    UserMapper userMapper;

    @Test
    public void insertTest() {
        userMapper.insert(UserEntity.builder()
                .user_id(1L)
                .order_id(System.currentTimeMillis()).build());
    }
}

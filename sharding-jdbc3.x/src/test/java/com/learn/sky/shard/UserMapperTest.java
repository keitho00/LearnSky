package com.learn.sky.shard;

import com.learn.sky.shard.dao.UserMapper;
import com.learn.sky.shard.entity.UserEntity;
import com.learn.sky.shard.entity.UserSexEnum;
import com.learn.tool.util.JsonUtils;
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
        for (long i = 0; i < 10L; i++) {
            userMapper.insert(UserEntity.builder()
                    .user_id(i)
                    .order_id(System.currentTimeMillis())
                    .userSex(UserSexEnum.MAN).build());
        }
    }

    @Test
    public void query() {
        userMapper.getAll().forEach(userEntity -> {
            System.out.println(JsonUtils.toJson(userEntity));
        });

    }
}

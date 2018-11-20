package com.learn.sky.shard;

import com.learn.sky.shard.dao.OpLogMapper;
import com.learn.sky.shard.entity.OpLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: JiuBuKong
 * @Date: 2018/11/2 下午4:02
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OpLogMapperTest {

    @Resource
    OpLogMapper opLogMapper;
    @Test
    public void insertTest(){
        opLogMapper.insert(OpLog.builder()
                .opType(1)
                .refType(1)
                .refId(1)
                .operator("111")
                .createTime(1L)
                .build());
    }


}

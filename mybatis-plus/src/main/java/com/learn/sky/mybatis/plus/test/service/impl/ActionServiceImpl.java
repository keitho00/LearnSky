package com.learn.sky.mybatis.plus.test.service.impl;

import com.learn.sky.mybatis.plus.test.entity.Action;
import com.learn.sky.mybatis.plus.test.mapper.ActionMapper;
import com.learn.sky.mybatis.plus.test.service.IActionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WangHao
 * @since 2018-06-12
 */
@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper, Action> implements IActionService {

}

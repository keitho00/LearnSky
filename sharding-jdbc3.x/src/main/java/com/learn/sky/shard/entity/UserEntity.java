package com.learn.sky.shard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: JiuBuKong
 * @Date: 2018/11/2 上午11:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 2352729475687163376L;
    private Long id;
    private Long order_id;
    private Long user_id;
    private String userName;
    private String passWord;
    private UserSexEnum userSex;
    private String nickName;


}

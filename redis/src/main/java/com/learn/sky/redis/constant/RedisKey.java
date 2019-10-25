package com.learn.sky.redis.constant;

public class RedisKey {


    /**
     * 接口添加的用户每天可使用免费抽奖次数 String key:activityId+userId+cycleDay
     */
    public static String FREE_USER_DAY_COUNT = "lo:u:f:d:c";

    /**
     * 接口添加的用户活动期间可使用免费抽奖次数 String  key:activityId+userId
     */
    public static String FREE_USER_ACTIVITY_COUNT = "lo:u:f:a:c";

    /**
     * 用户每天已使用免费抽奖次数 String key:activityId+userId+cycleDay
     */
    public static String FREE_USER_USED_DAY_COUNT = "lo:u:u:f:d:c";

    /**
     * 用户每天鱼翅抽奖次数（用户判断阶梯抽奖）String key:activityId+userId+cycleDay
     */
    public static String YU_CHI_USER_USED_DAY_COUNT ="lo:u:u:y:d:c";
    /**
     * 用户活动期间已使用免费抽奖次数 String key:activityId+userId
     */
    public static String FREE_USER_USED_ACTIVITY_COUNT = "lo:u:u:f:a:c";

    /**
     * 每天奖品中奖次数 String key:activityId+prizeId+cycleDay
     */
    public static String WIN_PRIZE_DAY_COUNT = "lo:w:p:d:c";

    /**
     * 活动期间奖品中奖次数 String key:activityId+prizeId
     */
    public static String WIN_PRIZE_ACTIVITY_COUNT = "lo:w:p:a:c";

    /**
     * 用户每天某个奖品中奖次数 String key:activityId+prizeId+userId+cycleDay
     */
    public static String USER_WIN_PRIZE_DAY_COUNT = "lo:u:w:p:d:c";

    /**
     * 用户活动期间某个奖品中奖次数  map key:activityId+prizeId mapKey:userId
     */
    public static String USER_WIN_PRIZE_ACTIVITY_COUNT = "lo:u:w:p:a:c";
}

package com.learn.sky.redis.rule;


import static com.learn.sky.redis.constant.RedisKey.*;

/**
 * @Author: JiuBuKong
 * @Date: 2019/10/16 5:58 PM
 */
public class KeyRule {

    private static final String SEPARATOR = ":";

    public static String freeUserDayCount(Long activityId, Long userId, Long cycleDay) {
        return key(FREE_USER_DAY_COUNT, activityId, userId, cycleDay);
    }

    public static String freeUserActivityCount(Long activityId, Long userId) {
        return key(FREE_USER_ACTIVITY_COUNT, activityId, userId, null);
    }

    public static String freeUserUsedDayCount(Long activityId, Long userId, Long cycleDay) {
        return key(FREE_USER_USED_DAY_COUNT, activityId, userId, cycleDay);
    }

    public static String freeUserUsedActivityCount(Long activityId, Long userId) {
        return key(FREE_USER_USED_ACTIVITY_COUNT, activityId, userId, null);
    }

    public static String yuchiUserUsedDayCount(Long activityId, Long userId, Long cycleDay){
        return key(YU_CHI_USER_USED_DAY_COUNT, activityId, userId, cycleDay);
    }


    public static String winPrizeDayCount(Long activityId, Long prizeId, Long cycleDay) {
        return key(WIN_PRIZE_DAY_COUNT, activityId, null, cycleDay, prizeId);
    }

    public static String winPrizeActivityCount(Long activityId, Long prizeId) {
        return key(WIN_PRIZE_ACTIVITY_COUNT, activityId, null, null, prizeId);
    }

    public static String winUserPrizeDayCount(Long activityId, Long prizeId, Long userId, Long cycleDay) {
        return key(USER_WIN_PRIZE_DAY_COUNT, activityId, userId, cycleDay, prizeId);
    }

    public static String winUserPrizeActivityCount(Long activityId, Long prizeId) {
        return key(USER_WIN_PRIZE_ACTIVITY_COUNT, activityId, null, null, prizeId);
    }

    private static String key(String prefix, Long activityId, Long userId, Long cycleDay) {
        return key(prefix, activityId, userId, cycleDay, null);
    }


    private static String key(String prefix, Long activityId, Long userId, Long cycleDay, Long prizeId) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix)
                .append(SEPARATOR)
                .append(activityId);
        if (userId != null) {
            sb.append(SEPARATOR)
                    .append(userId);
        }
        if (cycleDay != null) {
            sb.append(SEPARATOR)
                    .append(cycleDay);
        }
        if (prizeId != null) {
            sb.append(SEPARATOR)
                    .append(prizeId);
        }
        return sb.toString();
    }


}

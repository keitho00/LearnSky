package com.learn.sky.web.lock;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Slf4j
@Aspect
@Component
public class DistributionLockAspect {

    private static final Random RANDOM = new Random();

    private static final Long MIX_EXPIRE_TIME = 2L;

    private static long count = 0;
    @Resource
    RedisTemplate redisTemplate;

    @Around("@annotation(com.learn.sky.web.lock.DLock)")
    public Object around(ProceedingJoinPoint joinPoint) {
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();

            DLock dLock = method.getAnnotation(DLock.class);
            if (dLock != null) {
                String lockedPrefix = buildLockedPrefix(dLock, method, joinPoint.getArgs());
                long timeOut = dLock.timeOut();
                int expireTime = dLock.expireTime();
                long value = System.currentTimeMillis();
                if (lock(lockedPrefix, timeOut, expireTime, value)) {
                    try {
                        return joinPoint.proceed();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    } finally {
                        unlock(lockedPrefix, value);
                    }
                } else {
                    recheck(lockedPrefix, expireTime);
                }

            }
        } catch (Exception e) {
            log.error("DLock around error", e);
        }
        return null;
    }

    public void sync(String lockedPrefix, long timeOut, int expireTime, Runnable runnable) {
        try {
            long value = System.currentTimeMillis();
            if (lock(lockedPrefix, timeOut, expireTime, value)) {
                try {
                    runnable.run();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                } finally {
                    unlock(lockedPrefix, value);
                }
            } else {
                recheck(lockedPrefix, expireTime);
            }
        } catch (Exception e) {
            log.error("DLock sync error", e);
        }
    }

    /**
     * 检查超时时间设置
     *
     * @param lockedPrefix
     * @param expireTime
     */
    private void recheck(String lockedPrefix, int expireTime) {
        try {
            Long ttl = redisTemplate.getExpire(getLockedPrefix(lockedPrefix), TimeUnit.SECONDS);
            if (ttl == null || ttl < 0) {
                Long value = (Long) redisTemplate.<String, Long>opsForValue().get(getLockedPrefix(lockedPrefix));
                //没有超时设置则设置超时
                if (value != null) {
                    long oldTime = value;
                    long newTime = expireTime * 1000 - (System.currentTimeMillis() - oldTime);
                    if (newTime < 0) {
                        //已过超时时间  设默认最小超时时间
                        redisTemplate.expire(getLockedPrefix(lockedPrefix), MIX_EXPIRE_TIME, TimeUnit.SECONDS);
                    } else {
                        //未超过  设置为剩余超时时间
                        redisTemplate.expire(getLockedPrefix(lockedPrefix), newTime, TimeUnit.SECONDS);
                    }
                    log.info(lockedPrefix + "recheck:" + newTime);
                }
            }
            log.info(String.format("执行失败lockedPrefix:%s count:%d", lockedPrefix, count++));
        } catch (Exception e) {
            log.error("DLockAspect recheck error", e);
        }
    }

    /**
     * 解锁
     *
     * @param lockedPrefix
     * @param value
     */
    private void unlock(String lockedPrefix, long value) {
        try {
            Long kvValue = (Long) redisTemplate.<String, Long>opsForValue().get(getLockedPrefix(lockedPrefix));
            if (kvValue != null && kvValue == value) {
                redisTemplate.delete(getLockedPrefix(lockedPrefix));
                log.info(lockedPrefix + "unlock:" + kvValue + "----" + value);
            } else {
                log.info(lockedPrefix + "is not lock now");
            }

        } catch (Exception e) {
            log.error("unlock error" + getLockedPrefix(lockedPrefix), e);
        }
    }

    /**
     * 加锁
     *
     * @param lockedPrefix
     * @param timeOut
     * @param expireTime
     * @param value
     * @return
     */
    private boolean lock(String lockedPrefix, long timeOut, int expireTime, long value) {
        long millisTime = System.currentTimeMillis();
        try {
            //在timeOut的时间范围内不断轮询锁
            while (System.currentTimeMillis() - millisTime < timeOut * 1000) {
                //锁不存在的话，设置锁并设置锁过期时间，即加锁
                Boolean res = (Boolean) redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                    //定义序列化方式
                    RedisSerializer<Long> serializer = redisTemplate.getDefaultSerializer();
                    byte[] value1 = serializer.serialize(value);
                    boolean flag = redisConnection.setNX(getLockedPrefix(lockedPrefix).getBytes(), value1);
                    return flag;
                });
                if (res) {
                    Boolean expireRes = redisTemplate.expire(getLockedPrefix(lockedPrefix), expireTime, TimeUnit.SECONDS);
                    log.info(lockedPrefix + "locked and expire " + expireRes);
                    return true;
                }
                //短暂休眠，避免可能的活锁
                Thread.sleep(100, RANDOM.nextInt(50000));
            }
        } catch (Exception e) {
            log.error("lock error " + getLockedPrefix(lockedPrefix), e);
        }

        return false;
    }

    private String getLockedPrefix(String lockedPrefix) {
        return "DLock:" + lockedPrefix;
    }

    private String buildLockedPrefix(DLock dLock, Method method, Object[] args) {
        String prefix = dLock.prefix();
        int[] argsIndex = dLock.argsIndex();
        StringBuffer sbPrefix = new StringBuffer();
        if (StringUtils.isEmpty(prefix)) {
            sbPrefix.append(method.getDeclaringClass().getSimpleName());
            sbPrefix.append(":");
            sbPrefix.append(method.getName());
        } else {
            sbPrefix.append(prefix);
        }

        if (argsIndex.length != 0) {
            for (int i : argsIndex) {
                if (args[i] != null) {
                    sbPrefix.append(args[i].toString());
                }
            }
        }
        return sbPrefix.toString();
    }
}

package com.tian.usercenter.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tian.usercenter.model.User;
import com.tian.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class PreCacheJob {
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private List<Long> mainUserList = Arrays.asList(1L);

    @Scheduled(cron = "0 12 1 * * *")
    public void doCacheRecommendUser(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Page<User> userPage = userService.page(new Page<>(1, 20), queryWrapper);
        String redisKey = String.format("shayu:user:recommend:%s", mainUserList);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        try {
            valueOperations.set(redisKey, userPage, 30000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("redis set key error",e);
        }

    }
}

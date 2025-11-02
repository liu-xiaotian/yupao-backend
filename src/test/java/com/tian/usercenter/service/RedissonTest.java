package com.tian.usercenter.service;

import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class RedissonTest {
    @Resource
    private RedissonClient redissonClient;
    @Test
    void test(){
        List<String> list = new ArrayList<>();
        list.add("tian");
        System.out.println("list" + list.get(0));

        list.remove(0);

        RList<String> rlist = redissonClient.getList("test-list");
        rlist.add("tian");
        System.out.println("rlist" + rlist.get(0));
        rlist.remove(0);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("tian", 10);
        map.get("tian");

        RMap<Object, Object> map1 = redissonClient.getMap("test-map");
    }
}

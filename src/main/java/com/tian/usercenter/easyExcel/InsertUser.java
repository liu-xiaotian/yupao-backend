package com.tian.usercenter.easyExcel;

import com.tian.usercenter.mapper.UserMapper;
import com.tian.usercenter.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

/**
 * @ClassName: yupao-backend
 * @Description:
 */
@Component
public class InsertUser {

    @Resource
    private UserMapper userMapper;

    /**
     * 循环插入用户
     */
//    @Scheduled(initialDelay = 5000,fixedRate = Long.MAX_VALUE )
    public void doInsertUser() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 1000;
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("假沙鱼");
            user.setUserAccount("yusha");
            user.setAvatarUrl("shanghai.myqcloud.com/shayu931/shayu.png");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("123456789108");
            user.setEmail("shayu-yusha@qq.com");
            user.setUserStatus(0);
            user.setUserRole(0);
            user.setTags("[]");
            userMapper.insert(user);
        }
        stopWatch.stop();
        System.out.println( stopWatch.getLastTaskTimeMillis());

    }
}
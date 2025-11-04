package com.tian.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.usercenter.model.domain.UserTeam;
import com.tian.usercenter.service.UserTeamService;
import com.tian.usercenter.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author Tian
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2025-11-04 14:58:12
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}





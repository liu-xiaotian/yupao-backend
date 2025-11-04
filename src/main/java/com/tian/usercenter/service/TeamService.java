package com.tian.usercenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.usercenter.model.domain.Team;
import com.tian.usercenter.model.domain.User;

/**
* @author Tian
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2025-11-04 14:58:04
*/
public interface TeamService extends IService<Team> {
    long addTeam(Team team, User loginUser);
}

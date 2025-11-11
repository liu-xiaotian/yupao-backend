package com.tian.usercenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.usercenter.model.domain.Team;
import com.tian.usercenter.model.domain.User;
import com.tian.usercenter.model.request.TeamJoinRequest;
import com.tian.usercenter.model.request.TeamQuery;
import com.tian.usercenter.model.request.TeamUpdateRequest;
import com.tian.usercenter.model.vo.TeamUserVO;

import java.util.List;

/**
* @author Tian
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2025-11-04 14:58:04
*/
public interface TeamService extends IService<Team> {
    long addTeam(Team team, User loginUser);

    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

    /**
     * 修改队伍
     * @param teamUpdateRequest
     * @param loginUser
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @param loginUser
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);
}

package com.tian.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tian.usercenter.common.BaseResponse;
import com.tian.usercenter.common.ErrorCode;
import com.tian.usercenter.common.ResultUtils;
import com.tian.usercenter.exception.BusinessException;
import com.tian.usercenter.model.User;
import com.tian.usercenter.model.domain.UserLoginRequest;
import com.tian.usercenter.model.domain.UserRegisterRequest;
import com.tian.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.tian.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.tian.usercenter.constant.UserConstant.USER_LOGIN_STATE;
import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/*用户接口*/
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:5173"}, allowCredentials = "true")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;
    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest){
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
            String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, httpServletRequest);
        return ResultUtils.success(user);
    }
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request){
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", "%" + username + "%");   // 模糊匹配
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }
    @GetMapping("/search/tags")
    public BaseResponse<List<User>> searchUserByTags(@RequestParam(required = false) List<String> tagNameList){
        if (CollectionUtils.isEmpty(tagNameList)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<User> userList = userService.searchUsersByTags(tagNameList);
        return ResultUtils.success(userList);
    }
    @GetMapping("/recommend")
    public BaseResponse<Page<User>> recommendUsers(long pageSize, long pageNum, HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        String redisKey = String.format("shayu:user:recommend:%s", loginUser.getId());
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //如有缓存直接读取
        Page<User> userPage = (Page<User>)  valueOperations.get(redisKey);
        if (userPage != null){
            return ResultUtils.success(userPage);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Page<User> userList = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        //写缓存 10s 过期
        try {
            valueOperations.set(redisKey, userPage, 30000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("set redisKey error", e);
        }
        return ResultUtils.success(userList);
    }
    @PostMapping("/update")
    public BaseResponse<Integer> update(@RequestBody User user, HttpServletRequest request){
        //验证参数是否为空
        if (user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //鉴权
        User loginUser = userService.getLoginUser(request);
        int result = userService.updateUser(user, loginUser);
        return ResultUtils.success(result);
    }
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request){
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 是否为管理员
     */
    private boolean isAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user!=null && user.getUserRole() == ADMIN_ROLE;
    }

    /**
     * 登录状态管理
     *
     * @param request
     * @return
     */
    @GetMapping("current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        long userId = currentUser.getId();
        //校验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    @PostMapping("/layout")
    public BaseResponse<Integer> userLogout(@RequestBody HttpServletRequest httpServletRequest){
        if (httpServletRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLayout(httpServletRequest);
        return ResultUtils.success(result);
    }
}

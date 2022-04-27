package com.smk.cpp.sts.business.system.controller;

import com.smk.cpp.sts.base.model.PageModel;
import com.smk.cpp.sts.base.model.ResultModel;
import com.smk.cpp.sts.base.model.UserInfoModel;
import com.smk.cpp.sts.business.system.entity.UserEntity;
import com.smk.cpp.sts.business.system.service.UserService;
import com.smk.cpp.sts.business.system.vo.UserVo;
import com.smk.cpp.sts.common.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年12月31日 12:03
 * @Description:
 */
@RestController
@RequestMapping("/userMgr/v1/")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("getUserPageList/{pageSize}/{currentPageNo}")
    public ResultModel<PageModel<UserVo>> getUserPageList(UserEntity user,
          @PathVariable int pageSize, @PathVariable int currentPageNo) {
        PageModel<UserVo> userPageList = 
                userService.getUserPageList(pageSize, currentPageNo, user);
        return ResultUtils.success(userPageList);
    }

    @GetMapping("getUserByUserId/{userId}")
    public ResultModel<UserInfoModel> getUserByUserId(@PathVariable String userId) {
        UserInfoModel userInfo = userService.getUserInfoByUserId(userId);
        return ResultUtils.success(userInfo);
    }
    
    @PostMapping("saveUser")
    public ResultModel<String> saveUser(@RequestBody UserEntity user){
        userService.saveUser(user);
        return ResultUtils.success();
    }
    
    @PutMapping("updateUser")
    public ResultModel<String> updateUser(@RequestBody UserEntity user){
        userService.updateUser(user);
        return ResultUtils.success();
    }
    
    @DeleteMapping("deleteUserByUserId/{userId}")
    public ResultModel<String> deleteUserByUserId(@PathVariable String userId){
        userService.deleteUserByUserId(userId);
        return ResultUtils.success();
    }
    
}

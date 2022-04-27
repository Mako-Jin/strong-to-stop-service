package com.smk.cpp.sts.common.controller;

import com.smk.cpp.sts.base.model.ResultModel;
import com.smk.cpp.sts.base.model.UserDetailsModel;
import com.smk.cpp.sts.base.model.UserInfoModel;
import com.smk.cpp.sts.common.service.LoginService;
import com.smk.cpp.sts.common.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:40
 */
@RestController
@RequestMapping("/auth/v1")
public class LoginController {
    
    @Autowired
    private LoginService loginService;

    /**
     * 获取当前用户
     */
    @GetMapping("/current-user")
    public ResultModel<UserInfoModel> currentUser(){
        UserInfoModel userInfo = loginService.getCurrentUser();
        return ResultUtils.success(userInfo);
    }

    @PostMapping("/token-refresh")
    public ResultModel<Map<String, String>> refreshToken(@RequestBody Map<String, String> token) {
        Map<String, String> tokenMap = loginService.refreshToken(token);
        return ResultUtils.success(tokenMap);
    }
    
}

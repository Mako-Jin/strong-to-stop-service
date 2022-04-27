package com.smk.cpp.sts.common.service;

import com.alibaba.fastjson.JSONObject;
import com.smk.cpp.sts.base.constant.IConstants;
import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.base.model.AuthorityModel;
import com.smk.cpp.sts.base.model.UserDetailsModel;
import com.smk.cpp.sts.base.model.UserInfoModel;
import com.smk.cpp.sts.business.system.service.UserService;
import com.smk.cpp.sts.common.util.JwtTokenUtils;
import com.smk.cpp.sts.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月21日 23:17
 * @Description:
 */
@Service
public class LoginService {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    
    @Autowired
    private UserService userService;
    
    /**
     * @Description:刷新token
     */
    public Map<String, String> refreshToken(Map<String, String> token) {
        if (!token.containsKey(IConstants.ACCESS_TOKEN) || !token.containsKey(IConstants.REFRESH_TOKEN)) {
            logger.debug("刷新token == old token： {}", token);
            throw new ServiceException(ResultStatusEnums.COMMON_PARAM_CHECK_ERROR);
        }
        // 获取refresh_token和access_token
        String accessToken = token.get(IConstants.ACCESS_TOKEN);
        String refreshToken = token.get(IConstants.REFRESH_TOKEN);
        // 检查token是否过期
        if (JwtTokenUtils.isTokenExpired(refreshToken)) {
            throw new ServiceException(ResultStatusEnums.TOKEN_IS_EXPIRED);
        }
        // 检查refresh_token和access_token有效性和关联性
        if (!JwtTokenUtils.getAudienceFromToken(accessToken)
                .equals(JwtTokenUtils.getAudienceFromToken(refreshToken))) {
            throw new ServiceException(ResultStatusEnums.AUTHENTICATION_TOKEN_CHECK_FAILED);
        }
        UserDetailsModel userDetail = JwtTokenUtils.getUserFromToken(accessToken);
        logger.debug("刷新token == userDetail： {}", userDetail);
        refreshToken = JwtTokenUtils.createRefreshToken(userDetail);
        accessToken = JwtTokenUtils.createRefreshToken(userDetail);
        // 重新生成refresh_token和access_token
        Map<String, String> result = new HashMap<>(2);
        result.put(IConstants.ACCESS_TOKEN, refreshToken);
        result.put(IConstants.REFRESH_TOKEN, accessToken);
        return result;
    }
    
    /**
     * @Description:获取当前用户信息
     */
    public UserInfoModel getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoModel userInfo = null;
        // 匿名用户用户信息封装
        // userId: uuid, username: "anonymousUser", role: "whiteList", avatar: "default"
        if ("anonymousUser".equals(authentication.getPrincipal())) {
            Set<AuthorityModel> roles = Set.copyOf(JSONObject.parseArray(
                    "[{\"roleId\":\"whitelist\", \"roleName\": \"whitelist\", \"isNeedAuthorized\": 2}]"
                    , AuthorityModel.class
            )) ;
            userInfo = new UserInfoModel(
                StringUtil.idGenerate(), 
                "anonymousUser", 
                "anonymous", 
                "",
                roles
            );
        } else {
            UserDetailsModel userDetailsModel = (UserDetailsModel)authentication.getPrincipal();
            String userId = userDetailsModel.getUserId();
            userInfo = userService.getUserInfoByUserId(userId);
        }
        return userInfo;
    }
}

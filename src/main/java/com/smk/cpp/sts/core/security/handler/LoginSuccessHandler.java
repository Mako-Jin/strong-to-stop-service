package com.smk.cpp.sts.core.security.handler;

import com.alibaba.fastjson.JSON;
import com.smk.cpp.sts.base.constant.IConstants;
import com.smk.cpp.sts.base.model.UserDetailsModel;
import com.smk.cpp.sts.common.util.JwtTokenUtils;
import com.smk.cpp.sts.common.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:35
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, Authentication authentication)
            throws IOException, ServletException {
        logger.debug("login success!");
        //设置编码
        httpServletResponse.setContentType(IConstants.APPLICATION_JSON_UTF8_VALUE);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 生成Token
        UserDetailsModel userDetails = (UserDetailsModel) authentication.getPrincipal();
        Map<String, String> result = new HashMap<>(2);
        String accessToken = JwtTokenUtils.createAccessToken(userDetails);
        String refreshToken = JwtTokenUtils.createRefreshToken(userDetails);
        result.put(IConstants.ACCESS_TOKEN, accessToken);
        result.put(IConstants.REFRESH_TOKEN, refreshToken);
        httpServletResponse.getWriter().write(JSON.toJSONString(ResultUtils.success(result)));
    }

}

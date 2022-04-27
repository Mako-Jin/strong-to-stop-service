package com.smk.cpp.sts.core.security.handler;

import com.alibaba.fastjson.JSON;
import com.smk.cpp.sts.base.constant.IConstants;
import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.model.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:36
 */
@Component
public class LogoutHandler implements LogoutSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(LogoutHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        response.setContentType(IConstants.APPLICATION_JSON_UTF8_VALUE);
        response.setHeader("Access-Control-Allow-Origin", "*");
        ResultModel<String> result = new ResultModel<>(true, ResultStatusEnums.SUCCESS_STATUS.getCode(), "欢迎下次再来。");
        response.getWriter().write(JSON.toJSONString(result));
    }
}

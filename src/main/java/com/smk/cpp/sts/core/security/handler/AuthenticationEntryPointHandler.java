package com.smk.cpp.sts.core.security.handler;

import com.alibaba.fastjson.JSON;
import com.smk.cpp.sts.base.constant.IConstants;
import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.model.ResultModel;
import com.smk.cpp.sts.common.util.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:32
 */
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPointHandler.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, 
                         AuthenticationException e) throws IOException, ServletException {
        logger.debug("AuthenticationEntryPointHandler is {}", e.getMessage());
        // 防止乱码
        httpServletResponse.setContentType(IConstants.APPLICATION_JSON_UTF8_VALUE);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        ResultModel<String> result = new ResultModel<>(false, 
                ResultStatusEnums.NOT_LOGIN_EXCEPTION.getCode(),
                MessageUtils.getMessage(ResultStatusEnums.NOT_LOGIN_EXCEPTION.getMessage()));
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }

}

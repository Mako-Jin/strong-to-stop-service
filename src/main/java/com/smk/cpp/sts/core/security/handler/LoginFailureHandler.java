package com.smk.cpp.sts.core.security.handler;

import com.alibaba.fastjson.JSON;
import com.smk.cpp.sts.base.constant.IConstants;
import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.SecurityServiceException;
import com.smk.cpp.sts.base.model.ResultModel;
import com.smk.cpp.sts.common.util.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:40
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {
        logger.debug("login failure reason is {}", exception.getMessage());
        response.setContentType(IConstants.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ResultStatusEnums resultStatusEnums = getAuthenticationException(exception);
        response.setHeader("Access-Control-Allow-Origin", "*");
        ResultModel<String> result = new ResultModel<>
                (false, resultStatusEnums.getCode(), 
                    MessageUtils.getMessage(resultStatusEnums.getMessage()));
        response.getWriter().write(JSON.toJSONString(result));
    }

    private ResultStatusEnums getAuthenticationException(AuthenticationException exception) {
        if (exception instanceof ProviderNotFoundException) {
            return ResultStatusEnums.INTERNAL_SERVER_ERROR;
        } else if (exception instanceof BadCredentialsException) {
            return ResultStatusEnums.BAD_CREDENTIALS;
        } else if (exception instanceof AuthenticationServiceException) {
            return ResultStatusEnums.valueOf(exception.getMessage());
        } else if (exception instanceof SecurityServiceException) {
            return ResultStatusEnums.valueOf(exception.getMessage());
        } else {
            return ResultStatusEnums.INTERNAL_SERVER_ERROR;
        }
    }
    
}

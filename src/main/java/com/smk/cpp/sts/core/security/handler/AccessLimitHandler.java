package com.smk.cpp.sts.core.security.handler;

import com.alibaba.fastjson.JSON;
import com.smk.cpp.sts.base.constant.IConstants;
import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.model.ResultModel;
import com.smk.cpp.sts.common.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:21
 */
@Component
public class AccessLimitHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(AccessLimitHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        logger.debug("AccessDeniedHandler error is {}", accessDeniedException.getMessage());
        response.setContentType(IConstants.APPLICATION_JSON_UTF8_VALUE);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        ResultStatusEnums resultStatusEnums = ResultStatusEnums.valueOf(accessDeniedException.getMessage());
        ResultModel<String> result = ResultUtils.error(resultStatusEnums);
        response.getWriter().write(JSON.toJSONString(result));
    }
}

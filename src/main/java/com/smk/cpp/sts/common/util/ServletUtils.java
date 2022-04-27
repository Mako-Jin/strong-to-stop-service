package com.smk.cpp.sts.common.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月22日 17:25
 * @Description:
 */
public class ServletUtils {

    /**
     * 从header或者参数中获取token
     * @return token
     */
    public static String getToken(HttpServletRequest request, String key){
        String token = request.getHeader(key);
        if(!StringUtils.hasLength(token)){
            token=request.getParameter(key);
        }
        return token;
    }
    
}

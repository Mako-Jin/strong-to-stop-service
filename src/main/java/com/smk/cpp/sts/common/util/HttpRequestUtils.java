package com.smk.cpp.sts.common.util;

import com.alibaba.fastjson.JSONObject;
import com.smk.cpp.sts.base.constant.IConstants;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:33
 */
public class HttpRequestUtils {

    /**
     * 获取请求Body
     * @param request
     * @return
     */
    public static String getBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = null;
        try {    
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(request.getReader());
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
    
    public static String getParameterValue (HttpServletRequest request, 
            String keyName, String requestBody) {
        if (!StringUtils.hasText(keyName)) {
            return "";
        }
        JSONObject body = JSONObject.parseObject(requestBody);
        if (body != null && body.getString(keyName) != null) {
            return body.getString(keyName);
        } else if (request.getParameter(keyName) != null) {
            return request.getParameter(keyName);
        } else if (request.getHeader(keyName) != null) {
            return request.getHeader(keyName);
        } else {
            return "";
        }
    }
    
}

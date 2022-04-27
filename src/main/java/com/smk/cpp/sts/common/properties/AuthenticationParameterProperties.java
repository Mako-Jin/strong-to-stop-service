package com.smk.cpp.sts.common.properties;

import com.smk.cpp.sts.base.model.AuthenticationParameterModel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年09月01日 9:14
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = AuthenticationParameterProperties.CURRENT_PROPERTIES_PREFIX)
public class AuthenticationParameterProperties {
    
    public static final String CURRENT_PROPERTIES_PREFIX = "sts.auth";
    
    private Map<String, AuthenticationParameterModel> param;

    public Map<String, AuthenticationParameterModel> getParam() {
        return param;
    }

    public void setParam(Map<String, AuthenticationParameterModel> param) {
        this.param = param;
    }
}

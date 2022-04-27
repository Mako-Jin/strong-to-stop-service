package com.smk.cpp.sts.base.constant;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:36
 */
public interface IConstants {

    String JWT_ACCESS_TOKEN_KEY = "smk_Authorization";
    
    String JWT_REFRESH_TOKEN_KEY = "smk_Authorization_Refresh";

    String COMPANY_ORGANIZATION_NAME = "SanMako";

    String JWT_TOKEN_PREFIX = COMPANY_ORGANIZATION_NAME + " ";

    String JWT_TOKEN_SECRET_SALT = "4b55ab99d96b47ffae8b166ab599b65e";
    
    Long DEFAULT_JWT_TOKEN_VALIDITY_IN_SECONDS = 30 * 60L;
    
    Long JWT_ACCESS_TOKEN_VALIDITY_IN_SECONDS = 30 * 60L;
    
    Long JWT_REFRESH_TOKEN_VALIDITY_IN_SECONDS = 2 * 60 * 60L;
    
    /**
     * token10s之内都算过期
     */
    Long PRE_EXPIRED_TIME = 30L;

    String URL_PARAMETER_IDENTIFIER_SYMBOL = "?";
    
    String USERNAME = "username";
    
    String PASSWORD = "password";
    
    String AUTHORIZES = "authorizes";
    
    String REFRESH_TOKEN = "refreshToken";
    
    String ACCESS_TOKEN = "accessToken";

    String REGEX_UNDERLINE = "_";

    String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

    String REQUEST_BODY_CACHE_KEY = "requestBody";
    
    int TREE_TOP_LEVEL = 1;
    
    String TREE_TOP_ID = "STS_TREE_TOP_ID";
    
    String DEFAULT_PASSWORD = "Sts#@!123";
    
}

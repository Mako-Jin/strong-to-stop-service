package com.smk.cpp.sts.base.model;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年09月01日 9:30
 * @Description:
 */
public class AuthenticationParameterModel {
    
    private String tokenClassName;
    
    private String usernameParameter;
    
    private String passwordParameter;
    
    private boolean hasImageCode = false;
    
    private String imageCodeParameter;

    public String getTokenClassName() {
        return tokenClassName;
    }

    public void setTokenClassName(String tokenClassName) {
        this.tokenClassName = tokenClassName;
    }

    public String getUsernameParameter() {
        return usernameParameter;
    }

    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    public String getPasswordParameter() {
        return passwordParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }

    public boolean isHasImageCode() {
        return hasImageCode;
    }

    public void setHasImageCode(boolean hasImageCode) {
        this.hasImageCode = hasImageCode;
    }

    public String getImageCodeParameter() {
        return imageCodeParameter;
    }

    public void setImageCodeParameter(String imageCodeParameter) {
        this.imageCodeParameter = imageCodeParameter;
    }
}

package com.smk.cpp.sts.base.model;

import java.util.Set;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月13日 1:05
 * @Description:
 */
public class UserInfoModel {
    
    private String userId;
    
    private String username;
    
    private String nickName;
    
    private String avatar;

    private String phoneNum;

    private String emailAddress;
    
    private Set<AuthorityModel> roles;

    public UserInfoModel() {
    }

    public UserInfoModel(String userId, String username, String avatar) {
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
    }

    public UserInfoModel(String userId, String username, String nickName, String avatar) {
        this.userId = userId;
        this.username = username;
        this.nickName = nickName;
        this.avatar = avatar;
    }

    public UserInfoModel(String userId, String username, String nickName, String avatar, Set<AuthorityModel> roles) {
        this.userId = userId;
        this.username = username;
        this.nickName = nickName;
        this.avatar = avatar;
        this.roles = roles;
    }

    public UserInfoModel(String userId, String username, String avatar, Set<AuthorityModel> roles) {
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<AuthorityModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<AuthorityModel> roles) {
        this.roles = roles;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}

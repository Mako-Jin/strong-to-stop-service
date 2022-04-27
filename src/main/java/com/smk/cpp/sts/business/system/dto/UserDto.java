package com.smk.cpp.sts.business.system.dto;

import com.smk.cpp.sts.business.system.entity.UserEntity;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年04月11日 18:11
 * @Description:
 */
public class UserDto {
    
    private UserEntity user;
    
    private List<String> roleIds;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }
}

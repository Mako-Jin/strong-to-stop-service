package com.smk.cpp.sts.business.system.entity;

import java.util.Date;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:35
 */
public class RoleEntity {
    
    private String roleId;
    
    private String roleName;
    
    private String roleDesc;

    /**
     * 是否公开；1：是；2：否
     */
    private int isSecret = 1;
    
    /**
     * 是否需要被授权；1：是；2：否
     */
    private int isNeedAuthorized = 1;

    /**
     * 1: 启用；2、禁用
     */
    private int roleStatus = 1;
    
    private Date createTime;
    
    private Date updateTime;
    
    private String createUser;
    
    private String updateUser;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public int getIsSecret() {
        return isSecret;
    }

    public void setIsSecret(int isSecret) {
        this.isSecret = isSecret;
    }

    public int getIsNeedAuthorized() {
        return isNeedAuthorized;
    }

    public void setIsNeedAuthorized(int isNeedAuthorized) {
        this.isNeedAuthorized = isNeedAuthorized;
    }

    public int getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(int roleStatus) {
        this.roleStatus = roleStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}

package com.smk.cpp.sts.base.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:32
 */
public class AuthorityModel implements GrantedAuthority {

    private String roleId;

    private String roleName;

    /**
     * 是否需要被授权；1：是；2：否
     */
    private int isNeedAuthorized;

    public AuthorityModel(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public AuthorityModel(String roleId, String roleName, int isNeedAuthorized) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.isNeedAuthorized = isNeedAuthorized;
    }

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

    @Override
    public String getAuthority() {
        return this.roleName;
    }

    public int getIsNeedAuthorized() {
        return isNeedAuthorized;
    }

    public void setIsNeedAuthorized(int isNeedAuthorized) {
        this.isNeedAuthorized = isNeedAuthorized;
    }
}

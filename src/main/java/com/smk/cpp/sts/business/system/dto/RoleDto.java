package com.smk.cpp.sts.business.system.dto;

import com.smk.cpp.sts.business.system.entity.RoleEntity;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年04月11日 18:06
 * @Description:
 */
public class RoleDto {
    
    private RoleEntity role;
    
    private String roleId;
    
    private List<String> menuIds;

    public List<String> getMenuIds() {
        return menuIds;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}

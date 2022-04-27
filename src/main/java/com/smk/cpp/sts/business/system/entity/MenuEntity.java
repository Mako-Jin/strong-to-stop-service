package com.smk.cpp.sts.business.system.entity;

import com.smk.cpp.sts.base.model.AbstractTreeNodeModel;

import java.util.Date;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:35
 */
public class MenuEntity extends AbstractTreeNodeModel {

    private String menuId;
    
    private String menuName;
    
    private String menuUrl;

    private String menuDesc;
    
    /**
     * 1: menu; 2: page; 3: operation
     */
    private int menuType;
    
    /**
     * 1: 启用；2、禁用
     */
    private int menuStatus = 1;
    
    private int sort;
    
    /**
     * 是否保密；1：是；2：否
     */
    private int isSecret;
    
    private int menuLevel;
    
    private Date createTime;
    
    private Date updateTime;

    private String createUser;

    private String updateUser;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIsSecret() {
        return isSecret;
    }

    public void setIsSecret(int isSecret) {
        this.isSecret = isSecret;
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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public int getMenuStatus() {
        return menuStatus;
    }

    public void setMenuStatus(int menuStatus) {
        this.menuStatus = menuStatus;
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

    public int getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(int menuLevel) {
        this.menuLevel = menuLevel;
    }

    @Override
    public String getId() {
        return this.menuId;
    }

    @Override
    public int getTreeLevel() {
        return this.menuLevel;
    }

}

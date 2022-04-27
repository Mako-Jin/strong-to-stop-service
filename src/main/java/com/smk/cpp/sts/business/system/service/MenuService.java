package com.smk.cpp.sts.business.system.service;

import com.smk.cpp.sts.business.system.entity.MenuEntity;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月25日 10:07
 * @Description:
 */
public interface MenuService {

    /**
     * 获取白名单列表
     * @return java.util.List<java.lang.String>
     */
    List<String> getSystemWhiteList();

    /**
     * 获取需要授权的菜单列表
     * @return java.util.List<com.smk.cpp.sts.business.system.entity.MenuEntity>
     */
    List<MenuEntity> getAuthorizedMenuList();
    
    /**
     * 获取菜单列表，树形结构
     * @param menuEntity MenuEntity
     * @return java.util.List<com.smk.cpp.sts.business.system.entity.MenuEntity>
     */
    List<MenuEntity> getMenuTreeList(MenuEntity menuEntity);
    
    /**
     * 新增菜单
     * @param menuEntity MenuEntity
     */
    void saveMenu(MenuEntity menuEntity);

    /**
     * 根据菜单id更新菜单
     * @param menuEntity MenuEntity
     */
    void updateMenuByMenuId(MenuEntity menuEntity);
    
    /**
     * 根据菜单id删除菜单以及子菜单
     * @param menuId String
     */
    void deleteMenuByMenuId(String menuId);

    /**
     * 根据父id获取子菜单列表
     * @param parentId String
     * @return java.util.List<com.smk.cpp.sts.business.system.entity.MenuEntity>
     */
    List<MenuEntity> getMenuListByParentId(String parentId);
    
    /**
     * 根据角色id获取菜单列表
     * @param roleId 角色id
     * @return java.util.List<com.smk.cpp.sts.business.system.entity.MenuEntity>
     */
    List<String> getMenuIdsByRoleId(String roleId);
    
}

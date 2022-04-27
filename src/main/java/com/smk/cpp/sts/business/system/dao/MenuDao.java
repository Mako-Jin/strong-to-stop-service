package com.smk.cpp.sts.business.system.dao;

import com.smk.cpp.sts.business.system.entity.MenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月25日 10:09
 * @Description:
 */
@Mapper
public interface MenuDao {

    /**
     * 获取系统白名单
     * @return java.util.List<java.lang.String>
     */
    List<String> getSystemWhiteList();

    /**
     * 获取需要授权的菜单列表
     * @return java.util.List<com.smk.cpp.sts.business.system.entity.MenuEntity>
     */
    List<MenuEntity> getAuthorizedMenuList();
    
    /**
     * 根据父id获取子菜单列表
     * @param parentId String
     * @return java.util.List<com.smk.cpp.sts.business.system.entity.MenuEntity>
     */
    List<MenuEntity> getMenuListByParentId(String parentId);

    /**
     * 根据角色id获取子菜单列表
     * @param roleId String
     * @return java.util.List<com.smk.cpp.sts.business.system.entity.MenuEntity>
     */
    List<String> getMenuIdsByRoleId(String roleId);
    
    /**
     * 获取所有菜单
     * @param menuEntity MenuEntity
     * @return java.util.List<com.smk.cpp.sts.business.system.entity.MenuEntity>
     */
    List<MenuEntity> getAllMenuList(MenuEntity menuEntity);
    
    /**
     * 新增菜单
     * @param menuEntity MenuEntity
     * @return int
     */
    int saveMenu(MenuEntity menuEntity);

    /**
     * 根据id更新菜单
     * @param menuEntity MenuEntity
     * @return int
     */
    int updateMenuByMenuId(MenuEntity menuEntity);
    
    /**
     * 批量删除菜单
     * @param menuIds List<String>
     * @return int
     */
    int batchDelete(List<String> menuIds);
    
    /**
     * 批量删除角色菜单关系
     * @param menuIds List<String>
     */
    void batchDeleteRelationRoleMenu(List<String> menuIds);
    
    /**
     * 根据url查询详细菜单信息
     * @param url String
     * @return com.smk.cpp.sts.business.system.entity.MenuEntity
     */
    MenuEntity getMenuByUrl(String url);
    
    /**
     * 获取当前父节点下的最大排序号
     * @param parentId String
     * @return com.smk.cpp.sts.business.system.entity.MenuEntity
     */
    int getMaxSortByParentId(String parentId);
    
}

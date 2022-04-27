package com.smk.cpp.sts.business.system.service;

import com.smk.cpp.sts.base.model.AuthorityModel;
import com.smk.cpp.sts.base.model.PageModel;
import com.smk.cpp.sts.business.system.dto.RoleDto;
import com.smk.cpp.sts.business.system.entity.RoleEntity;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月18日 18:57
 * @Description:
 */
public interface RoleService {

    /**
     * 通过请求地址获取角色列表
     * @param menuUrl
     * @return java.util.List<com.smk.cpp.sts.base.model.AuthorityModel>
     */
    List<AuthorityModel> getRoleListByMenuUrl(String menuUrl);
    
    /**
     * 根绝菜单id获取权限列表
     * @param menuId
     * @return java.util.List<com.smk.cpp.sts.base.model.AuthorityModel>
     */
    List<AuthorityModel> getRoleListByMenuId(String menuId);
    
    /**
     * 根绝菜单id获取权限列表
     * @param menuIds
     * @return java.util.List<com.smk.cpp.sts.base.model.AuthorityModel>
     */
    List<AuthorityModel> getRoleListByMenuIdList(List<String> menuIds);

    /**
     * 分页获取角色列表
     * @param pageSize
     * @param currentPageNo
     * @param role
     * @return com.smk.cpp.sts.base.model.PageModel<com.smk.cpp.sts.business.system.entity.RoleEntity>
     */
    PageModel<RoleEntity> getRolePageList(int pageSize, int currentPageNo, RoleEntity role);
    
    /**
     * 新增角色
     * @param role
     */
    void saveRole(RoleDto role);
    
    /**
     * 根据id更新角色
     * @param role
     */
    void updateRole(RoleEntity role);
    
    /**
     * 根据id删除角色
     * @param roleId
     */
    void deleteRoleByRoleId(String roleId);
    
    /**
     * 根据roleId查询角色信息
     * @param roleId String
     * @return com.smk.cpp.sts.business.system.entity.RoleEntity
     */
    RoleEntity getRoleByRoleId(String roleId);

    /**
     * 绑定角色菜单
     * @param role 角色和菜单关系
     */
    void bindingRoleMenu(RoleDto role);

    /**
     * 解绑角色菜单
     * @param role 角色和菜单关系
     */
    void unBundlingRoleMenu(RoleDto role);
    
}

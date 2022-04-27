package com.smk.cpp.sts.business.system.dao;

import com.smk.cpp.sts.base.model.AuthorityModel;
import com.smk.cpp.sts.business.system.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:35
 */
@Mapper
public interface RoleDao {
    
    /**
     * 根据用户id获取角色列表
     * @param userId String
     * @return java.util.Set<com.smk.cpp.sts.base.model.AuthorityModel>
     */
    Set<AuthorityModel> getRoleByUserId(String userId);
    
    /**
     * 根据url获取角色列表
     * @param menuUrl String
     * @return java.util.List<com.smk.cpp.sts.base.model.AuthorityModel>
     */
    List<AuthorityModel> getRoleListByMenuUrl(String menuUrl);
    
    /**
     * 根据菜单id获取角色列表
     * @param menuId String
     * @return java.util.List<com.smk.cpp.sts.base.model.AuthorityModel>
     */
    List<AuthorityModel> getRoleListByMenuId(String menuId);
    
    /**
     * 根据菜单id列表获取角色列表
     * @param menuIds List<String>
     * @return java.util.List<com.smk.cpp.sts.base.model.AuthorityModel>
     */
    List<AuthorityModel> getRoleListByMenuIdList(List<String> menuIds);

    /**
     * 获取满足条件的角色数量
     * @param role RoleEntity
     * @return int
     */
    int getRoleTotalCount(@Param("role") RoleEntity role);

    /**
     * 分页获取角色列表
     * @param role RoleEntity
     * @param startIndex int
     * @param offset int
     * @return java.util.List<com.smk.cpp.sts.business.system.entity.RoleEntity>
     */
    List<RoleEntity> getRolePageList(@Param("role") RoleEntity role, int startIndex, int offset);

    /**
     * 新增角色
     * @param role RoleEntity
     * @return int
     */
    int saveRole(RoleEntity role);
    
    /**
     * 根据角色id更新角色
     * @param role RoleEntity
     * @return int
     */
    int updateRoleByRoleId(RoleEntity role);

    /**
     * 根据roleId删除角色
     * @param roleId String
     * @return int
     */
    int deleteRoleByRoleId(String roleId);

    /**
     * 删除角色菜单关系
     * @param roleId String
     * @return int
     */
    int deleteRelationRoleMenuByRoleId(String roleId);
    
    /**
     * 删除用户角色关系
     * @param roleId String
     * @return int
     */
    int deleteRelationRoleUserByRoleId(String roleId);
    
    /**
     * 绑定菜单和角色的关系
     * @param roleId String
     * @param menuIds List<String>
     * @param userId String
     * @return int
     */
    int saveRelationRoleMenu(String roleId, List<String> menuIds, String userId);

    /**
     * 解绑菜单和角色的关系
     * @param roleId String
     * @param menuIds List<String>
     * @return int
     */
    int deleteRelationRoleMenu(String roleId, List<String> menuIds);

    /**
     * 根据roleId查询角色信息
     * @param roleId String
     * @return com.smk.cpp.sts.business.system.entity.RoleEntity
     */
    RoleEntity getRoleByRoleId(String roleId);

}

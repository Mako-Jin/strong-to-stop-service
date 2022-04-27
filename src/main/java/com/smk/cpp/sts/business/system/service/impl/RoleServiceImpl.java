package com.smk.cpp.sts.business.system.service.impl;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.base.model.AuthorityModel;
import com.smk.cpp.sts.base.model.PageModel;
import com.smk.cpp.sts.business.system.dao.RoleDao;
import com.smk.cpp.sts.business.system.dto.RoleDto;
import com.smk.cpp.sts.business.system.entity.RoleEntity;
import com.smk.cpp.sts.business.system.service.RoleService;
import com.smk.cpp.sts.common.util.PageUtils;
import com.smk.cpp.sts.common.util.SecurityUtils;
import com.smk.cpp.sts.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月18日 18:57
 * @Description:
 */
@Service
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<AuthorityModel> getRoleListByMenuUrl(String menuUrl) {
        return roleDao.getRoleListByMenuUrl(menuUrl);
    }

    @Override
    public List<AuthorityModel> getRoleListByMenuId(String menuId) {
        return roleDao.getRoleListByMenuId(menuId);
    }

    @Override
    public List<AuthorityModel> getRoleListByMenuIdList(List<String> menuIds) {
        return roleDao.getRoleListByMenuIdList(menuIds);
    }

    @Override
    public PageModel<RoleEntity> getRolePageList(int pageSize, int currentPageNo, RoleEntity role) {
        int count = roleDao.getRoleTotalCount(role);
        if (count < 1) {
            return new PageModel<>();
        }
        currentPageNo = PageUtils.getPageNum(currentPageNo, pageSize, count);
        int pageStartIndex = PageUtils.getPageStartIndex(currentPageNo, pageSize);
        List<RoleEntity> roleList = roleDao.getRolePageList(role, pageStartIndex, pageSize);
        return new PageModel<>(count, pageSize, currentPageNo, roleList);
    }

    @Override
    public void saveRole(RoleDto role) {
        RoleEntity roleEntity = role.getRole();
        roleEntity.setRoleId(StringUtil.idGenerate());
        int resultCount = roleDao.saveRole(roleEntity);
        if (resultCount < 1) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_SAVE_FAILED);
        }
        if (role.getMenuIds() != null && role.getMenuIds().size() > 0) {
            String currentUserId = SecurityUtils.getCurrentUserId();
            roleDao.saveRelationRoleMenu(roleEntity.getRoleId(), role.getMenuIds(), currentUserId);
        }
    }

    @Override
    public void updateRole(RoleEntity role) {
        if (!StringUtils.hasText(role.getRoleId())) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        int resultCount = roleDao.updateRoleByRoleId(role);
        if (resultCount < 1) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_UPDATE_FAILED);
        }
    }

    @Override
    public void deleteRoleByRoleId(String roleId) {
        if (!StringUtils.hasText(roleId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        int resultCount = roleDao.deleteRoleByRoleId(roleId);
        if (resultCount < 1) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_DELETE_FAILED);
        }
        roleDao.deleteRelationRoleMenuByRoleId(roleId);
        roleDao.deleteRelationRoleUserByRoleId(roleId);
    }

    @Override
    public RoleEntity getRoleByRoleId(String roleId) {
        if (!StringUtils.hasText(roleId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        return roleDao.getRoleByRoleId(roleId);
    }

    @Override
    public void bindingRoleMenu(RoleDto role) {
        if (StringUtils.hasText(role.getRoleId()) 
                && role.getMenuIds() != null 
                && role.getMenuIds().size() > 0) {
            String userId = SecurityUtils.getCurrentUserId();
            roleDao.saveRelationRoleMenu(role.getRoleId(), role.getMenuIds(), userId);
            return;
        }
        throw new ServiceException(ResultStatusEnums.COMMON_PARAM_CHECK_ERROR);
    }

    @Override
    public void unBundlingRoleMenu(RoleDto role) {
        if (StringUtils.hasText(role.getRoleId())
                && role.getMenuIds() != null
                && role.getMenuIds().size() > 0) {
            roleDao.deleteRelationRoleMenu(role.getRoleId(), role.getMenuIds());
            return;
        }
        throw new ServiceException(ResultStatusEnums.COMMON_PARAM_CHECK_ERROR);
    }

}

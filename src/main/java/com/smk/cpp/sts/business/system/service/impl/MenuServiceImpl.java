package com.smk.cpp.sts.business.system.service.impl;

import com.smk.cpp.sts.base.constant.MenuConstants;
import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.business.system.dao.MenuDao;
import com.smk.cpp.sts.business.system.entity.MenuEntity;
import com.smk.cpp.sts.business.system.service.MenuService;
import com.smk.cpp.sts.common.util.StringUtil;
import com.smk.cpp.sts.common.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月25日 10:07
 * @Description:
 */
@Service
public class MenuServiceImpl implements MenuService {
    
    @Autowired
    private MenuDao menuDao;
    
    @Override
    public List<String> getSystemWhiteList() {
        return menuDao.getSystemWhiteList();
    }

    @Override
    public List<MenuEntity> getAuthorizedMenuList() {
        return menuDao.getAuthorizedMenuList();
    }

    @Override
    public List<MenuEntity> getMenuTreeList(MenuEntity menuEntity) {
        List<MenuEntity> menuLists = menuDao.getAllMenuList(menuEntity);
        return (List<MenuEntity>) TreeUtils.getWholeTree(menuLists, menuEntity.getMenuId());
    }

    @Override
    public void saveMenu(MenuEntity menuEntity) {
        menuEntity.setMenuId(StringUtil.idGenerate());
        if (!StringUtils.hasText(menuEntity.getParentId())) {
            menuEntity.setParentId(MenuConstants.MENU_TOP_PARENT_ID);
        }
        MenuEntity menu = menuDao.getMenuByUrl(menuEntity.getMenuUrl());
        if (menu != null) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_IS_EXISTED);
        }
        int sort = menuDao.getMaxSortByParentId(menuEntity.getParentId());
        menuEntity.setSort(sort+getNextSort(menuEntity.getMenuType()));
        int resultCount = menuDao.saveMenu(menuEntity);
        if (resultCount < 1) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_SAVE_FAILED);
        }
    }
    
    private int getNextSort(int type) {
        int sortOffset;
        switch (type) {
            case 1:
                sortOffset = 100;
                break;
            case 2:
                sortOffset = 10;
                break;
            default:
                sortOffset = 1;
                break;
        }
        return sortOffset;
    }

    @Override
    public void updateMenuByMenuId(MenuEntity menuEntity) {
        if (!StringUtils.hasText(menuEntity.getMenuId())) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        int resultCount = menuDao.updateMenuByMenuId(menuEntity);
        if (resultCount < 1) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_UPDATE_FAILED);
        }
    }

    @Override
    public void deleteMenuByMenuId(String menuId) {
        if (!StringUtils.hasText(menuId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        List<MenuEntity> menuLists = menuDao.getAllMenuList(new MenuEntity());
        List<String> menuIds = TreeUtils.getSubTreeNodeIds(menuLists, menuId);
        menuIds.add(menuId);
        int resultCount = menuDao.batchDelete(menuIds);
        if (resultCount < 1) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_DELETE_FAILED);
        }
        menuDao.batchDeleteRelationRoleMenu(menuIds);
    }

    @Override
    public List<MenuEntity> getMenuListByParentId(String parentId) {
        if (!StringUtils.hasText(parentId)) {
            parentId = MenuConstants.MENU_TOP_PARENT_ID;
        }
        return menuDao.getMenuListByParentId(parentId);
    }

    @Override
    public List<String> getMenuIdsByRoleId(String roleId) {
        if (!StringUtils.hasText(roleId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        return menuDao.getMenuIdsByRoleId(roleId);
    }

}

package com.smk.cpp.sts.business.system.controller;

import com.smk.cpp.sts.base.model.ResultModel;
import com.smk.cpp.sts.business.system.entity.MenuEntity;
import com.smk.cpp.sts.business.system.service.MenuService;
import com.smk.cpp.sts.common.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年01月12日 10:07
 * @Description:
 */
@RestController
@RequestMapping("/menuMgr/v1/")
public class MenuController {
    
    @Autowired
    private MenuService menuService;

    @GetMapping("getMenuTreeList")
    public ResultModel<MenuEntity> getWholeMenuTreeList(MenuEntity menuEntity) {
        List<MenuEntity> menuTreeList = menuService.getMenuTreeList(menuEntity);
        return ResultUtils.success(menuTreeList);
    }
    
    @GetMapping("getMenuByParentId")
    public ResultModel<MenuEntity> getMenuList(String parentId) {
        List<MenuEntity> list = menuService.getMenuListByParentId(parentId);
        return ResultUtils.success(list);
    }

    @GetMapping("getMenuIdsByRoleId/{roleId}")
    public ResultModel<String> getMenuIdsByRoleId(@PathVariable String roleId) {
        List<String> list = menuService.getMenuIdsByRoleId(roleId);
        return ResultUtils.success(list);
    }
    
    @PostMapping("saveMenu")
    public ResultModel<String> saveMenu(@RequestBody MenuEntity menuEntity) {
        menuService.saveMenu(menuEntity);
        return ResultUtils.success();
    }
    
    @PutMapping("updateMenuByMenuId")
    public ResultModel<String> updateMenuByMenuId(@RequestBody MenuEntity menuEntity) {
        menuService.updateMenuByMenuId(menuEntity);
        return ResultUtils.success();
    }
    
    @DeleteMapping("deleteMenuByMenuId/{menuId}")
    public ResultModel<String> deleteMenuByMenuId(@PathVariable String menuId) {
        menuService.deleteMenuByMenuId(menuId);
        return ResultUtils.success();
    }
    
}

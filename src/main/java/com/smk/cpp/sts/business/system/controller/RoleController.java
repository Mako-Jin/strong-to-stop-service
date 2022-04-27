package com.smk.cpp.sts.business.system.controller;

import com.smk.cpp.sts.base.model.PageModel;
import com.smk.cpp.sts.base.model.ResultModel;
import com.smk.cpp.sts.business.system.dto.RoleDto;
import com.smk.cpp.sts.business.system.entity.RoleEntity;
import com.smk.cpp.sts.business.system.service.RoleService;
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

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年01月12日 10:07
 * @Description:
 */
@RestController
@RequestMapping("/roleMgr/v1/")
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    @GetMapping("getRolePageList/{pageSize}/{currentPageNo}")
    public ResultModel<PageModel<RoleEntity>> getUserPageList(RoleEntity role, 
          @PathVariable int pageSize, @PathVariable int currentPageNo) {
        PageModel<RoleEntity> rolePageList =
                roleService.getRolePageList(pageSize, currentPageNo, role);
        return ResultUtils.success(rolePageList);
    }

    @GetMapping("getRoleByRoleId/{roleId}")
    public ResultModel<RoleEntity> getRoleByRoleId(@PathVariable String roleId) {
        RoleEntity role = roleService.getRoleByRoleId(roleId);
        return ResultUtils.success(role);
    }

    @PostMapping("saveRole")
    public ResultModel<String> saveRole(@RequestBody RoleDto role) {
        roleService.saveRole(role);
        return ResultUtils.success();
    }
    
    @PostMapping("bindingRoleMenu")
    public ResultModel<String> bindingRoleMenu(@RequestBody RoleDto role) {
        roleService.bindingRoleMenu(role);
        return ResultUtils.success();
    }
    
    @PutMapping("updateRole")
    public ResultModel<String> updateRole(@RequestBody RoleEntity role) {
        roleService.updateRole(role);
        return ResultUtils.success();
    }
    
    @DeleteMapping("deleteRoleByRoleId/{roleId}")
    public ResultModel<String> deleteRoleByRoleId(@PathVariable String roleId) {
        roleService.deleteRoleByRoleId(roleId);
        return ResultUtils.success();
    }

    @DeleteMapping("unBundlingRoleMenu")
    public ResultModel<String> unBundlingRoleMenu(@RequestBody RoleDto role) {
        roleService.unBundlingRoleMenu(role);
        return ResultUtils.success();
    }
    
}

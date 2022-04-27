package com.smk.cpp.sts.core.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.base.model.AuthorityModel;
import com.smk.cpp.sts.business.system.entity.MenuEntity;
import com.smk.cpp.sts.business.system.service.MenuService;
import com.smk.cpp.sts.business.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月18日 18:51
 * @Description:
 */
@Component
public class MetadataSourceHandler implements FilterInvocationSecurityMetadataSource {
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 用来实现ant风格的url匹配
     */
    private static AntPathMatcher antPathMatcher = new AntPathMatcher();
    
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        requestUrl = requestUrl.indexOf("?") > 0 
                ? requestUrl.substring(0, requestUrl.indexOf("?")) : requestUrl;
        List<ConfigAttribute> result = new ArrayList<>();
        List<AuthorityModel> roleList = getRoleList(requestUrl);
        for (AuthorityModel authorityModel : roleList) {
            ConfigAttribute conf = new SecurityConfig(JSONObject.toJSONString(authorityModel));
            result.add(conf);
        }
        return result;
    }
    
    private List<AuthorityModel> getRoleList(String requestUrl) throws IllegalArgumentException {
        // 查询所有url
        List<MenuEntity> allMenuList = menuService.getAuthorizedMenuList();
        //查询白名单列表
        List<String> menuIds = new ArrayList<>();
        for (MenuEntity menu : allMenuList) {
            if (antPathMatcher.match(menu.getMenuUrl(), requestUrl)) {
                menuIds.add(menu.getMenuId());
            }
        }
        if(menuIds.size() > 0) {
            return roleService.getRoleListByMenuIdList(menuIds);
        }
        throw new ServiceException(ResultStatusEnums.MENU_IS_NOT_EXISTED);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}

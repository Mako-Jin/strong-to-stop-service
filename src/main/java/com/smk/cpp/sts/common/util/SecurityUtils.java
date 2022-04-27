package com.smk.cpp.sts.common.util;

import com.smk.cpp.sts.base.model.UserDetailsModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月17日 16:42
 * @Description:
 */
public class SecurityUtils {
    
    public static String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ("anonymousUser".equals(authentication.getPrincipal())) {
            return "";
        }
        UserDetailsModel userDetails = (UserDetailsModel) authentication.getPrincipal();
        return userDetails.getUserId();
    }
    
}

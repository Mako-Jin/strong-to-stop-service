package com.smk.cpp.sts.core.security.vote;

import com.alibaba.fastjson.JSONObject;
import com.smk.cpp.sts.base.constant.RoleConstants;
import com.smk.cpp.sts.base.model.AuthorityModel;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年01月02日 14:20
 * @Description:
 */
public class WhiteListVoteHandler implements AccessDecisionVoter<Object> {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        AuthorityModel needRole = JSONObject.parseObject(
                attribute.getAttribute(), AuthorityModel.class);
        if (needRole.getIsNeedAuthorized() == RoleConstants.IS_NOT_NEED_AUTHORIZED) {
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object
            , Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return ACCESS_DENIED;
        }
        // 弃权
        int result = ACCESS_ABSTAIN;  
        for (ConfigAttribute attribute : attributes) {
            if (supports(attribute)) {
                return ACCESS_GRANTED;
            }
        }
        return result;
    }

}

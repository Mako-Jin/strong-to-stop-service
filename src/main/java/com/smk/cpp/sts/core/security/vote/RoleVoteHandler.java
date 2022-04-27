package com.smk.cpp.sts.core.security.vote;

import com.alibaba.fastjson.JSONObject;
import com.smk.cpp.sts.base.model.AuthorityModel;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年01月05日 10:11
 * @Description:
 */
public class RoleVoteHandler  implements AccessDecisionVoter<Object> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return ACCESS_DENIED;
        }
        for (ConfigAttribute attribute : attributes) {
            AuthorityModel needRole = JSONObject.parseObject(
                    attribute.getAttribute(), AuthorityModel.class);
            for (GrantedAuthority authorityModel : authentication.getAuthorities()) {
                if (needRole.getAuthority().trim().equals(authorityModel.getAuthority())) {
                    return ACCESS_GRANTED;
                }
            }
        }
        return ACCESS_DENIED;
    }
}

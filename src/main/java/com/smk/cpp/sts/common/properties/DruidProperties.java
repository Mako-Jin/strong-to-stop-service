/***********************************************************************/
/**            Copyright (C) 2020-2030 西安三码客软件科技有限公司            */
/**                      All rights reserved                           */
/***********************************************************************/

package com.smk.cpp.sts.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:33
 */
@Component
@ConfigurationProperties(prefix = DruidProperties.DRUID_PREFIX)
public class DruidProperties {

    public static final String DRUID_PREFIX = "sts.druid";

    private String druidUserName;

    private String druidPassword;

    private String allow;

    private String deny;

    public String getDruidUserName() {
        return druidUserName;
    }

    public void setDruidUserName(String druidUserName) {
        this.druidUserName = druidUserName;
    }

    public String getDruidPassword() {
        return druidPassword;
    }

    public void setDruidPassword(String druidPassWord) {
        this.druidPassword = druidPassWord;
    }

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public String getDeny() {
        return deny;
    }

    public void setDeny(String deny) {
        this.deny = deny;
    }
}

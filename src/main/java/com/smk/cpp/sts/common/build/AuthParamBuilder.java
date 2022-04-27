package com.smk.cpp.sts.common.build;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月31日 19:19
 * @Description:
 */
public class AuthParamBuilder {
    
    private DefaultAuthParam defaultAuthParam;
    
    
    public class DefaultAuthParam {
        
        private String username;
        
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    
    // public class SmsCodeParam {
    //    
    //     private String address;
    //    
    //     private String 
    //    
    // }
    
}

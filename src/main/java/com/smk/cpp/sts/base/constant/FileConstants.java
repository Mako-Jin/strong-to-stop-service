package com.smk.cpp.sts.base.constant;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年09月03日 9:31
 * @Description:
 */
public interface FileConstants {
    
    String SETTINGS_CONFIG_PATH ="config";

    String PRIVATE_KEY_PATH = System.getProperty("user.dir") + "/Statics/secret/private_key.pem";

    String PUBLIC_KEY_PATH = System.getProperty("user.dir") + "/Statics/secret/public_key.pem";
    
    String FILE_TOP_PARENT_ID = "file_top_parent_id";
    
    int FILE_UPLOAD_STATUS_DONE = 3;
    
    int FILE_UPLOAD_STATUS_CREATED = 1;
    
    int CATALOG_IS_DIRECTORY = 1;
    
}

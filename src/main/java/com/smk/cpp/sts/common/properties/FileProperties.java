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
@ConfigurationProperties(prefix = FileProperties.FILE_PROPERTIES_PREFIX)
public class FileProperties {

    public static final String FILE_PROPERTIES_PREFIX = "sts.files";

    private static String baseDir;
    
    private static String uploadDir;
    
    private static String secretDir;

    private static FileProperties.Upload upload = new Upload();

    public static Upload getUpload() {
        return upload;
    }

    public void setUpload(Upload upload) {
        FileProperties.upload = upload;
    }

    public static String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        FileProperties.baseDir = baseDir;
    }

    public static String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        FileProperties.uploadDir = uploadDir;
    }

    public static String getSecretDir() {
        return secretDir;
    }

    public void setSecretDir(String secretDir) {
        FileProperties.secretDir = secretDir;
    }

    public static class Upload {

        public Upload() {
        }

        private long defaultChunkSize = 2L;

        public long getDefaultChunkSize() {
            return defaultChunkSize;
        }

        public void setDefaultChunkSize(long defaultChunkSize) {
            this.defaultChunkSize = defaultChunkSize;
        }
    }
    
}
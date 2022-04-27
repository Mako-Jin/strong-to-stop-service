package com.smk.cpp.sts.core.file.download;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月25日 10:58
 * @Description:
 */
public interface DownLoadStrategy {

    /**
     * 下载文件
     * 
     * @Author: Jin-LiangBo
     * @Date: 2022/4/8 16:24
     * @param response 响应
     * @param filename 文件名称
     * @param downloadSize 文件块大小
     * @param startPosition 开始位置
     * @throws FileNotFoundException f                      
     */
    void downloadFile(HttpServletResponse response, String filename, long downloadSize,
                      long startPosition) throws FileNotFoundException;
    
}

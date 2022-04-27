package com.smk.cpp.sts.core.file.download;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.common.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年04月07日 10:41
 * @Description:
 */
public class LocalDownloadStrategy implements DownLoadStrategy{
    
    private static final Logger logger = LoggerFactory.getLogger(LocalDownloadStrategy.class);
    
    @Override
    public void downloadFile(HttpServletResponse response, String filename, long downloadSize,
             long startPosition) throws FileNotFoundException {
        File downloadFile = new File(FileUtils.getUploadPath() + File.separator + filename);
        if (!downloadFile.exists()) {
            throw new FileNotFoundException(filename);
        }
        OutputStream out = null;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(downloadFile,"r");
            out = response.getOutputStream();
            //定位到指定开始位置
            if (startPosition > 0) {
                randomAccessFile.seek(startPosition);
            }
            int readLen;
            byte[] buffer = new byte[5120];
            while( downloadSize > 0 ) {
                readLen = randomAccessFile.read(buffer,0, Math.toIntExact(Math.min(5120, downloadSize)));
                downloadSize -= readLen;
                out.write(buffer, 0, readLen);
            }
            out.flush(); out.close(); randomAccessFile.close();
            response.flushBuffer();
        } catch(IOException e) {
            logger.error("download file error!==> {}", e.getMessage(), e);
            throw new ServiceException(ResultStatusEnums.DOWNLOAD_FILE_ERROR);
        } finally {
            if(out != null) {
                try { out.close(); }  catch (IOException e) {
                    logger.error("download out close error!==> {}", e.getMessage(), e);
                }
            }
        }
    }
    
}

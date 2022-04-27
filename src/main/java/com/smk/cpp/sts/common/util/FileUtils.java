package com.smk.cpp.sts.common.util;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.common.properties.FileProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月08日 10:51
 * @Description:
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 读取公私钥文件
     *
     * @param path 文件路径
     * @throws Exception 加载公钥时产生的异常
     */
    public static String loadKey(String path) throws SecurityException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String readLine;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new SecurityException("load file error!");
        } catch (NullPointerException e) {
            throw new SecurityException("file is null");
        }
    }

    public static List<String> getTargetDirectoryAllFileName(String directory) {
        File file = new File(directory);
        File[] array = file.listFiles();
        List<String> fileNames = new ArrayList<>();
        for (File f : array) {
            if (f.isFile()) {
                fileNames.add(f.getPath());
            } else if (f.isDirectory()) {
                fileNames.addAll(getTargetDirectoryAllFileName(f.getAbsolutePath()));
            }
        }
        return fileNames;
    }

    private static String getBasePath() {
        return System.getProperty("user.dir") + File.separator + FileProperties.getBaseDir();
    }

    public static String getUploadPath() {
        return String.join(File.separator, getBasePath(), FileProperties.getUploadDir());
    }
    
    public static String getUploadPath(String path) {
        return String.join(File.separator, getBasePath(), FileProperties.getUploadDir(), path);
    }

    public static String getSecretPath(String path) {
        return getBasePath() + File.separator + FileProperties.getSecretDir() + File.separator + path;
    }

    /**
     * 创建文件夹
     * @param directory directory
     */
    public static boolean createDirectory(String directory) {
        if (!StringUtils.hasLength(directory)) {
            throw new ServiceException(ResultStatusEnums.FILE_DIRECTORY_CREATE_FAIL);
        }
        return createDirectory(new File(directory));
    }

    /**
     * 创建文件夹
     * @param file file
     */
    public static boolean createDirectory(File file) {
        return file.exists() || file.mkdirs();
    }
    
    /**
     * 创建文件
     */
    public static File createFile(String filePath, String fileName) {
        if (!createDirectory(filePath)) {
            throw new ServiceException(ResultStatusEnums.FILE_DIRECTORY_CREATE_FAIL);
        }
        return new File(filePath, fileName);
    }
    
    /**
     * @Description: 重名文件夹重命名，按照 ${fileName}(n)的格式
     * @return null
     */
    public static String sameNameRename(String originalName, int n) {
        return String.format("%s (%s)", originalName, n);
    }

    public static void close(final Closeable closeable){
        if(closeable == null){
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            logger.error("close fail: {}", e.getMessage(), e);
        }
    }

    public static byte[] readFileToByteArray(final File file) throws IOException {
        try (InputStream in = openInputStream(file)) {
            final long fileLength = file.length();
            // file.length() may return 0 for system-dependent entities, treat 0 as unknown length - see IO-453
            return fileLength > 0 ? IOUtils.toByteArray(in, fileLength) : IOUtils.toByteArray(in);
        }
    }

    public static FileInputStream openInputStream(final File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canRead()) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

}

package com.smk.cpp.sts.business.file.service;

import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.vo.UploadVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月15日 11:10
 * @Description:
 */
public interface TransferService {

    /**
     * 上传文件
     * @param files 文件
     * @param uploadDto 文件信息
     * @return com.smk.cpp.sts.business.file.vo.UploadVo
     */
    UploadVo uploadFile(MultipartFile files, UploadDto uploadDto);
    
    /**
     * 检查文件标识符
     * @param uploadDto 文件信息
     * @return com.smk.cpp.sts.business.file.vo.UploadVo
     */
    UploadVo checkIdentifier(UploadDto uploadDto);
    
    /**
     * 预览文件
     * @param request 请求
     * @param response 响应
     * @param catalogId 目录id
     */
    void preview(HttpServletRequest request, HttpServletResponse response, String catalogId);
    
}

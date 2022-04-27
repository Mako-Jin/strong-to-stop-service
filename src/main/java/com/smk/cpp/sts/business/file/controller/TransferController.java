package com.smk.cpp.sts.business.file.controller;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.model.ResultModel;
import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.vo.UploadVo;
import com.smk.cpp.sts.business.file.service.TransferService;
import com.smk.cpp.sts.common.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月15日 11:09
 * @Description:
 */
@RestController
@RequestMapping("/transfer/v1/")
public class TransferController {
    
    @Autowired
    private TransferService transferService;
    
    @PostMapping("checkIdentifier")
    public ResultModel<UploadVo> checkIdentifier(@RequestBody UploadDto uploadDto) {
        UploadVo uploadVo = transferService.checkIdentifier(uploadDto);
        if (uploadVo != null && uploadVo.isUploadComplete()) {
            return ResultUtils.success(uploadVo, ResultStatusEnums.FILE_SPEED_UPLOAD_DONE);
        } else if (uploadVo != null && !uploadVo.isUploadComplete()) {
            return ResultUtils.success(uploadVo, ResultStatusEnums.FILE_UPLOAD_DOWN_PARTIAL);
        }
        return ResultUtils.success(new UploadVo(null), ResultStatusEnums.FILE_HAS_NOT_UPLOAD);
    }

    /**
     * 上传文件
     *
     * @param files
     * @return
     */
    @PostMapping("upload")
    public ResultModel<UploadVo> upload(MultipartFile files, UploadDto uploadDto) {
        UploadVo uploadVo = transferService.uploadFile(files, uploadDto);
        return ResultUtils.success(uploadVo);
    }
    
    /**
     * 文件预览
     */
    @GetMapping("preview/{catalogId}")
    public void preview(HttpServletRequest request, HttpServletResponse response, 
                    @PathVariable String catalogId) {
        transferService.preview(request, response, catalogId);
    }
    
}

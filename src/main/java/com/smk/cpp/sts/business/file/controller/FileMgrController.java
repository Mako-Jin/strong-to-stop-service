package com.smk.cpp.sts.business.file.controller;

import com.smk.cpp.sts.base.model.ResultModel;
import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.service.FileMgrService;
import com.smk.cpp.sts.business.file.vo.FileStatusVo;
import com.smk.cpp.sts.common.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月15日 10:58
 * @Description:
 */
@RestController
@RequestMapping("/fileMgr/v1/")
public class FileMgrController {
    
    @Autowired
    private FileMgrService fileMgrService;
    
    /**
     * @Description: 初始化上传文件
     */
    @PostMapping("initiateFile")
    public ResultModel<String> initiateFile (@RequestBody UploadDto uploadDto) {
        String catalogId = fileMgrService.initiateFile(uploadDto);
        return ResultUtils.success(catalogId);
    }
    
    @GetMapping("getUnCompleteFileList")
    public ResultModel<FileStatusVo> getUnCompleteFileList() {
        List<FileStatusVo> unCompleteFileList = fileMgrService.getUnCompleteFileList();
        return ResultUtils.success(unCompleteFileList);
    }

}

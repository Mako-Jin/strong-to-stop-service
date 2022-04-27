package com.smk.cpp.sts.business.file.controller;

import com.smk.cpp.sts.base.model.ResultModel;
import com.smk.cpp.sts.business.file.entity.CatalogEntity;
import com.smk.cpp.sts.business.file.service.CatalogMgrService;
import com.smk.cpp.sts.common.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年04月01日 17:54
 * @Description:
 */
@RestController
@RequestMapping("/catalogMgr/v1/")
public class CatalogMgrController {
    
    @Autowired
    private CatalogMgrService catalogMgrService;

    /**
     * 获取文件列表
     */
    @GetMapping("getCatalogList")
    public ResultModel<CatalogEntity> getFileList(CatalogEntity catalog) {
        List<CatalogEntity> fileList = catalogMgrService.getCatalogList(catalog);
        return ResultUtils.success(fileList);
    }

    /**
     * @Description: 新建文件夹
     */
    @PostMapping("createDirectory")
    public ResultModel<String> createDirectory (@RequestBody CatalogEntity catalog) {
        catalogMgrService.createDirectory(catalog);
        return ResultUtils.success();
    }

    /**
     * @Description: 文件夹更名，或更新
     */
    @PutMapping("renameCatalog")
    public ResultModel<String> renameCatalog (@RequestBody CatalogEntity catalog) {
        catalogMgrService.renameCatalog(catalog);
        return ResultUtils.success();
    }

    @DeleteMapping("deleteByCatalogId/{catalogId}")
    public ResultModel<String> deleteByCatalogId (@PathVariable String catalogId) {
        catalogMgrService.deleteByCatalogId(catalogId);
        return ResultUtils.success();
    }
    
}

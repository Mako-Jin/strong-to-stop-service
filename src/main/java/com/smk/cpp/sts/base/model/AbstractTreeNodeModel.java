package com.smk.cpp.sts.base.model;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年01月18日 10:54
 * @Description:
 */
public abstract class AbstractTreeNodeModel<T> implements Serializable {

    /**
     * 获取节点id
     * @return id
     */
    public abstract String getId();
    
    /**
     * 获取树层级
     * @return TreeLevel
     */
    public abstract int getTreeLevel();
    
    private String parentId;
    
    private List<T> children;

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}

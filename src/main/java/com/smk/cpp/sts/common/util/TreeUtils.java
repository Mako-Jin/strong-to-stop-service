package com.smk.cpp.sts.common.util;

import com.smk.cpp.sts.base.constant.IConstants;
import com.smk.cpp.sts.base.model.AbstractTreeNodeModel;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年02月03日 14:18
 * @Description:
 */
public class TreeUtils {

    /**
     * @Description: 获取树子节点
     * @Author: Jin-LiangBo
     * @Date: 2022/2/7 10:44
     * @param list
     * @param parentId
     * @return java.util.List<? extends com.smk.cpp.sts.base.model.TreeNodeModel>
     */
    public static List<? extends AbstractTreeNodeModel> getChildrenTree(
            List<? extends AbstractTreeNodeModel> list, String parentId){
        //获取所有子节点
        List<? extends AbstractTreeNodeModel> childTreeList = list.stream()
                .filter(n -> parentId.equals(n.getParentId()))
                .collect(Collectors.toList());
        for (int i = 0; i < childTreeList.size(); i++) {
            childTreeList.get(i).setChildren(
                getChildrenTree(list, childTreeList.get(i).getId())
            );
        }
        return childTreeList.size() > 0 ? childTreeList : null;
    }
    
    /**
     * @Description: List转为完整树菜单
     * @Author: Jin-LiangBo
     * @Date: 2022/2/7 10:45
     * @return null
     */
    public static List<? extends AbstractTreeNodeModel> getWholeTree(
            List<? extends AbstractTreeNodeModel> list, String treeId) {
        List<? extends AbstractTreeNodeModel> parentTreeList = 
                StringUtils.hasText(treeId) ?
                list.stream().filter(
                    n -> n.getId().equals(treeId)
                ).collect(Collectors.toList())
                : list.stream().filter(
                    n -> StringUtils.hasText(n.getParentId()) && n.getParentId().equals(IConstants.TREE_TOP_ID)
                ).collect(Collectors.toList());
        for (int i = 0; i < parentTreeList.size(); i++) {
            parentTreeList.get(i).setChildren(
                getChildrenTree(list, parentTreeList.get(i).getId())
            );
        }
        return parentTreeList;
    }

    /**
     * @Description: 获取所有子节点id
     * @Author: Jin-LiangBo
     * @Date: 2022/2/7 10:45
     * @return null
     */
    public static List<String> getSubTreeNodeIds(
            List<? extends AbstractTreeNodeModel> list, String treeId) {
        List<String> subTreeNodeIds = list.stream()
                .filter(n -> n.getParentId().equals(treeId))
                .map(n -> n.getId())
                .collect(Collectors.toList());
        for (int i = 0; i < subTreeNodeIds.size(); i++) {
            subTreeNodeIds.addAll(
                getSubTreeNodeIds(list, subTreeNodeIds.get(i))
            );
        }
        return subTreeNodeIds;
    }

    /**
     * @Description: 获取所有子节点id
     * @Author: Jin-LiangBo
     * @Date: 2022/2/7 10:45
     * @return null
     */
    public static List<String> getSubTreeNodeIds(
            List<? extends AbstractTreeNodeModel> list, List<String> treeIds) {
        List<String> subTreeNodeIds = new ArrayList<>();
        for (int i = 0; i < treeIds.size(); i++) {
            subTreeNodeIds.addAll(getSubTreeNodeIds(list, treeIds.get(i)));
        }
        return subTreeNodeIds;
    }
    
}

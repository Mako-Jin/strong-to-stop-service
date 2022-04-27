package com.smk.cpp.sts.common.util;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年12月31日 16:18
 * @Description:
 */
public class PageUtils {
    
    public static int getPageNum(int currentPageNo, int pageSize, int totalCount) {
        int totalPageCount = totalCount % pageSize != 0
            ? totalCount / pageSize + 1
            : totalCount / pageSize;
        int pageNum = Math.max(Math.min(currentPageNo, totalPageCount), 1);
        return pageNum;
    }

    public static int getPageStartIndex(int currentPageNo, int pageSize) {
        return ( currentPageNo - 1 ) * pageSize;
    }
    
}

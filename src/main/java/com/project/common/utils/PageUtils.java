package com.project.common.utils;

import com.project.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

// 分页查询工具类
@Component
@SuppressWarnings("all")
public class PageUtils {

    @Autowired
    @Qualifier("constant")
    private Constant constant;

    // 总页面数
    private int totalPages;

    // 当前所处页面 默认为 1
    private int currPage = 1;

    // 页面大小
    private int pageSize;

    // 数据总数
    private int rows;


    public int getTotalPages() {
        return totalPages;
    }


    public int getCurrPage() {
        return currPage;
    }


    public int getRows() {
        return rows;
    }

    public void setRows(int rows){
        this.rows = rows;
        this.totalPages = (rows % pageSize) == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
    }

    public void setCurrPage(int currPage){
        if (currPage <= 0 || currPage > totalPages){
            this.currPage = 1;
        }else {
            this.currPage = currPage;
        }
    }


    private void setPageSize(){
        this.pageSize = constant.getPageSize();
    }

    public int getPageSize() {
        return pageSize;
    }

}

package com.project.pojo;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PagesAO {

    @NotNull(message = "页数不能为空")
    @Size(min = 1)
    private int totalPages;

    @NotNull(message = "当前页面不能为空")
    @Size(min = 1)
    private int currentPage;

    @NotNull(message = "页面大小不能为空")
    @Size(min = 1)
    private int pageSize;
}

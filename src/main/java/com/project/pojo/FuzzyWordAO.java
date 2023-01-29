package com.project.pojo;

import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel("FuzzyWordAO实体类")
public class FuzzyWordAO {


    @NotNull(message = "词条不能为空")
    @Size(max = 50, min = 1, message = "数据长度不合法")
    @ApiModelProperty("词条")
    private String word;

    @Nullable
    @Size(max = 100, min = 0, message = "页面数不合法")
    @ApiModelProperty("当前页数")
    private String currPage;

    public String getWord() {
        return word;
    }


    public String getCurrPageOrDefault() {
        if (StringUtils.isNullOrEmpty(currPage)){
            return "1";
        }else {
            return this.currPage;
        }
    }

}

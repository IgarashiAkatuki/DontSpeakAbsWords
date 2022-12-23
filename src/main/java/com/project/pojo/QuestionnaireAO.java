package com.project.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel("QuestionnaireAO实体类")
public class QuestionnaireAO implements Serializable {

    @Max(value = 21,message = "问卷题目最大为20")
    @Min(value = 0,message = "问卷题目最少为1")
    @NotNull(message = "问卷题目数不能为空")
    @ApiModelProperty("问卷题目数量")
    private int limit;

    public QuestionnaireAO(){};

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "QuestionnaireAO{" +
                "limit=" + limit +
                '}';
    }
}

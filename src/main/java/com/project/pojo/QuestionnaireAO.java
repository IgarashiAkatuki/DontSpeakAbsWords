package com.project.pojo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class QuestionnaireAO implements Serializable {

    @Max(value = 20,message = "问卷题目最大为20")
    @Min(value = 5,message = "问卷题目最少为5")
    @NotNull(message = "问卷题目数不能为空")
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

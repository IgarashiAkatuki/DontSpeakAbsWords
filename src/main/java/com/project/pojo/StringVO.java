package com.project.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ApiModel("StringAO实体类")
public class StringVO implements Serializable {

    @NotNull(message = "参数不能为空")
    @Size(max = 100, message = "超出最大长度100")
    @ApiModelProperty("字符串")
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "StringVO{" +
                "str='" + str + '\'' +
                '}';
    }


}

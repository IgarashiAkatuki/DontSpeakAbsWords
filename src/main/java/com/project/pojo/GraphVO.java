package com.project.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class GraphVO implements Serializable {

    @NotNull(message = "数据不能为空")
    @Size(max = 30,min = 1,message = "数据长度不合法")
    private String val;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }


}

package com.project.pojo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TempSource implements Serializable {
    @NotNull(message = "id不能为空")
    private int id;
    @NotNull(message = "翻译不能为空")
    private String translation;
    @NotNull(message = "来源不能为空")
    private String source;

    public TempSource() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "TempSource{" +
                "id=" + id +
                ", translation='" + translation + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}

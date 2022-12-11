package com.project.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

// AO-在Web层和Service层传输数据用的对象 Application Object
public class WordAO implements Serializable {

    @NotNull(message = "数据不能为空")
    @Size(max = 100,min = 1,message = "数据长度不合法")
    private String word;

    public WordAO() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                '}';
    }
}

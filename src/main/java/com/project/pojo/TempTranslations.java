package com.project.pojo;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class TempTranslations implements Serializable {

    @NotNull(message = "id不能为空")
    private int id;

    @NotNull(message = "词条不能为空")
    @Size(max = 50,message = "词条长度过长")
    private String word;

    @NotNull(message = "释义不能为空")
    @Size(max = 50,message = "释义长度过长")
    private String translation;

    @Nullable
    private String newTranslation;

    public TempTranslations() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getNewTranslation() {
        return newTranslation;
    }

    public void setNewTranslation(String newTranslation) {
        this.newTranslation = newTranslation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TempTranslations{" +
                "word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", newTranslation='" + newTranslation + '\'' +
                '}';
    }
}

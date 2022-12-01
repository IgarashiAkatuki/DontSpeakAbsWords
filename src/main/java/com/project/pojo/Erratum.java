package com.project.pojo;

import java.util.Date;

public class Erratum {
    private int id;
    private String word;
    private String translation;
    private String reason;
    private Date date;

    public Erratum(){

    }

    public Erratum(int id, String word, String translation, String reason, Date date) {
        this.id = id;
        this.word = word;
        this.translation = translation;
        this.reason = reason;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Erratum{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", reason='" + reason + '\'' +
                ", date=" + date +
                '}';
    }
}

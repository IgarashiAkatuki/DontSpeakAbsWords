package com.project.entity.mysql;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

// 勘误
public class Erratum implements Serializable {

    private int id;

    private String word;

    private String translation;

    private Date date;

    private String reason;

    public Erratum() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
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

    public Date getDate() {
        return this.date == null ? null : (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Erratum{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", date=" + date +
                ", reason='" + reason + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Erratum erratum = (Erratum) o;
        return id == erratum.id && Objects.equals(word, erratum.word) && Objects.equals(translation, erratum.translation) && Objects.equals(date, erratum.date) && Objects.equals(reason, erratum.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, translation, date, reason);
    }
}

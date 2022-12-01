package com.project.pojo;

import java.util.Date;

public class Source {
    private int id;
    private String source;
    private String translation;
    private Date date;
    private String likes;

    public Source(){

    };

    public Source(int id, String source, String translation, Date date, String likes) {
        this.id = id;
        this.source = source;
        this.translation = translation;
        this.date = date;
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Source{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", translation='" + translation + '\'' +
                ", date=" + date +
                ", likes='" + likes + '\'' +
                '}';
    }
}

package com.midsummra.pojo;

import java.io.Serializable;
import java.util.Date;

public class Source implements Serializable {
    private int id;
    private String resource;
    private String translation;
    private Date date;
    private String likes;

    public Source(){

    };

    public Source(int id, String resource, String translation, Date date, String likes) {
        this.id = id;
        this.resource = resource;
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
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
        return "Resource{" +
                "id=" + id +
                ", resource='" + resource + '\'' +
                ", translation='" + translation + '\'' +
                ", date=" + date +
                ", likes='" + likes + '\'' +
                '}';
    }
}

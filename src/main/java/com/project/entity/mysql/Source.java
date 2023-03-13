package com.project.entity.mysql;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Source implements Serializable {

    private int id;

    private String source;

    private String translation;

    private String likes;

    private Date date;

    private String submitted;

    private String url;

    public Source() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
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

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public Date getDate() {
        return this.date == null ? null : (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source1 = (Source) o;
        return id == source1.id && Objects.equals(source, source1.source) && Objects.equals(translation, source1.translation) && Objects.equals(likes, source1.likes) && Objects.equals(date, source1.date) && Objects.equals(submitted, source1.submitted) && Objects.equals(url, source1.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source, translation, likes, date, submitted, url);
    }

    @Override
    public String toString() {
        return "Source{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", translation='" + translation + '\'' +
                ", likes='" + likes + '\'' +
                ", date=" + date +
                ", submitted='" + submitted + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

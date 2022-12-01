package com.project.pojo;

import java.util.Date;

public class Word {
    private int id;
    private Date date;
    private String word;
    private int likes;

    public Word(int id, Date date, String word, int likes) {
        this.id = id;
        this.date = date;
        this.word = word;
        this.likes = likes;
    }

    public Word(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", date=" + date +
                ", word='" + word + '\'' +
                ", likes=" + likes +
                '}';
    }
}

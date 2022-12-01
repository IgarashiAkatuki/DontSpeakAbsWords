package com.project.pojo;

import java.util.Date;

public class Translation {
    private int id;
    private Date date;
    private String word;
    private String translation;
    private int wordId;
    private int likes;
    public String source;

    public Translation(){

    }
    public Translation(int id, Date date, String word, String translation, int wordId, int likes) {
        this.id = id;
        this.date = date;
        this.word = word;
        this.translation = translation;
        this.wordId = wordId;
        this.likes = likes;
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

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "id=" + id +
                ", date=" + date +
                ", word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", wordId=" + wordId +
                ", likes=" + likes +
                '}';
    }
}

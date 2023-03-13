package com.project.entity.mysql;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Translation implements Serializable {

    private int id;

    private String word;

    private String translation;

    private int likes;

    private String source;

    private Date date;

    private int wordId;

    private String fuzzyWord;

    private String url;

    public Translation() {
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

    public Date getDate() {
        return this.date == null ? null : (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getFuzzyWord() {
        return fuzzyWord;
    }

    public void setFuzzyWord(String fuzzyWord) {
        this.fuzzyWord = fuzzyWord;
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
        Translation that = (Translation) o;
        return id == that.id && likes == that.likes && wordId == that.wordId && Objects.equals(word, that.word) && Objects.equals(translation, that.translation) && Objects.equals(source, that.source) && Objects.equals(date, that.date) && Objects.equals(fuzzyWord, that.fuzzyWord) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, translation, likes, source, date, wordId, fuzzyWord, url);
    }

    @Override
    public String toString() {
        return "Translation{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", likes=" + likes +
                ", source='" + source + '\'' +
                ", date=" + date +
                ", wordId=" + wordId +
                ", fuzzyWord='" + fuzzyWord + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

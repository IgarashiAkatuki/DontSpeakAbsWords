package com.project.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Word implements Serializable {

    private int wordId;

    private String word;

    private int likes;

    private Date date;

    public Word() {
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
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

    public Date getDate() {
        return (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return wordId == word1.wordId && Objects.equals(word, word1.word) && Objects.equals(likes, word1.likes) && Objects.equals(date, word1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordId, word, likes, date);
    }
}

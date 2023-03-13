package com.project.entity.mysql;

import java.util.Date;
import java.util.Objects;

public class TranslStatistics {

    private String word;

    private String translation;

    private int wordId;

    private Date date;

    private int popular = 0;

    private int outdated = 0;

    private String fluency;

    private int neutral = 0;

    private int commendation = 0;

    private int derogatory = 0;

    private String partOfSpeech;

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

    public Date getDate() {
        return this.date == null ? null : (Date) date.clone();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPopular() {
        return popular;
    }

    public void setPopular(int popular) {
        this.popular = popular;
    }

    public int getOutdated() {
        return outdated;
    }

    public void setOutdated(int outdated) {
        this.outdated = outdated;
    }

    public String getFluency() {
        return fluency;
    }

    public void setFluency(String fluency) {
        this.fluency = fluency;
    }

    public int getNeutral() {
        return neutral;
    }

    public void setNeutral(int neutral) {
        this.neutral = neutral;
    }

    public int getCommendation() {
        return commendation;
    }

    public void setCommendation(int commendation) {
        this.commendation = commendation;
    }

    public int getDerogatory() {
        return derogatory;
    }

    public void setDerogatory(int derogatory) {
        this.derogatory = derogatory;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslStatistics that = (TranslStatistics) o;
        return wordId == that.wordId && popular == that.popular && outdated == that.outdated && neutral == that.neutral && commendation == that.commendation && derogatory == that.derogatory && Objects.equals(word, that.word) && Objects.equals(translation, that.translation) && Objects.equals(date, that.date) && Objects.equals(fluency, that.fluency) && Objects.equals(partOfSpeech, that.partOfSpeech);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, translation, wordId, date, popular, outdated, fluency, neutral, commendation, derogatory, partOfSpeech);
    }

    @Override
    public String toString() {
        return "TranslStatistics{" +
                "word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", wordId=" + wordId +
                ", date=" + date +
                ", popular=" + popular +
                ", outdated=" + outdated +
                ", fluency='" + fluency + '\'' +
                ", neutral=" + neutral +
                ", commendation=" + commendation +
                ", derogatory=" + derogatory +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                '}';
    }
}

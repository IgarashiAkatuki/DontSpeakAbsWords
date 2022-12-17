package com.project.entity;

import java.io.Serializable;
import java.util.Date;


public class Statistics implements Serializable {

    private Date date;

    private int selectTranslCount;

    private int addWordCount;

    private int addTranslCount;

    private int QuestionnaireCount;

    public Statistics() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSelectTranslCount() {
        return selectTranslCount;
    }

    public void setSelectTranslCount(int selectTranslCount) {
        this.selectTranslCount = selectTranslCount;
    }

    public int getAddWordCount() {
        return addWordCount;
    }

    public void setAddWordCount(int addWordCount) {
        this.addWordCount = addWordCount;
    }

    public int getAddTranslCount() {
        return addTranslCount;
    }

    public void setAddTranslCount(int addTranslCount) {
        this.addTranslCount = addTranslCount;
    }

    public int getQuestionnaireCount() {
        return QuestionnaireCount;
    }

    public void setQuestionnaireCount(int questionnaireCount) {
        QuestionnaireCount = questionnaireCount;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "date=" + date +
                ", selectTranslCount=" + selectTranslCount +
                ", addWordCount=" + addWordCount +
                ", addTranslCount=" + addTranslCount +
                ", QuestionnaireCount=" + QuestionnaireCount +
                '}';
    }

}

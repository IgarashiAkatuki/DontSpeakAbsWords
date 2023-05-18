package com.project.pojo;

public class PageDTO {

    private String word;

    private int limit;

    private int startIndex;


    public PageDTO(String word, int limit, int startIndex) {
        this.word = word;
        this.limit = limit;
        this.startIndex = startIndex;
    }

    public PageDTO(){};

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}

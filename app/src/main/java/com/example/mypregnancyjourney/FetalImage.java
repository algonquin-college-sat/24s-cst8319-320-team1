package com.example.mypregnancyjourney;

public class FetalImage {
    private int id;
    private int week;
    private int imageResourceId;
    private String relatedArticleUrl;

    public FetalImage(int id, int week, int imageResourceId, String relatedArticleUrl) {
        this.id = id;
        this.week = week;
        this.imageResourceId = imageResourceId;
        this.relatedArticleUrl = relatedArticleUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getRelatedArticleUrl() {
        return relatedArticleUrl;
    }

    public void setRelatedArticleUrl(String relatedArticleUrl) {
        this.relatedArticleUrl = relatedArticleUrl;
    }
}

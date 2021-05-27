package com.proyectosm.mentalhealthapp.ui.settings;

public class InterestsModel {
    public Integer id;
    public String title;
    public boolean active;
    public double sentiment_index;

    public InterestsModel(Integer id, String title, double sentiment_index) {
        this.id = id;
        this.title = title;
        this.sentiment_index = sentiment_index;
    }

    public double getSentiment_index() {
        return sentiment_index;
    }

    public void setSentiment_index(double sentiment_index) {
        this.sentiment_index = sentiment_index;
    }

    public InterestsModel(Integer id, String title, double sentiment_index, boolean active) {
        this.id = id;
        this.title = title;
        this.active = active;
        this.sentiment_index = sentiment_index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

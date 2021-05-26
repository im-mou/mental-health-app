package com.proyectosm.mentalhealthapp.ui.notifications;

import com.proyectosm.mentalhealthapp.ui.dashboard.JournalModel;

public class CalendarModel {
    String date, color;
    float sentiment_index;
    JournalModel[] journal;

    public CalendarModel() {
    }

    public CalendarModel(String date, String color, float sentiment_index, JournalModel[] journal) {
        this.date = date;
        this.sentiment_index = sentiment_index;
        this.color = color;
        this.journal = journal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getSentiment_index() {
        return sentiment_index;
    }

    public void setSentiment_index(float sentiment_index) {
        this.sentiment_index = sentiment_index;
    }

    public JournalModel[] getJournal() {
        return journal;
    }

    public void setJournal(JournalModel[] journal) {
        this.journal = journal;
    }
}

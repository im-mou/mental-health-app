package com.proyectosm.mentalhealthapp;

import com.proyectosm.mentalhealthapp.ui.dashboard.JournalModel;

public class UserModel {
    String name, date, color;
    float sentiment_index;
    JournalModel[] journal;
    String[] intereses;

    // Constructor por parámetros
    public UserModel(String name, String date, String color, float sentiment_index, JournalModel[] journal, String[] intereses) {
        this.name = name;
        this.date = date;
        this.color = color;
        this.sentiment_index = sentiment_index;
        this.journal = journal;
        this.intereses = intereses;
    }

    // Funciones setters y getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String[] getIntereses() {
        return intereses;
    }

    public void setIntereses(String[] intereses) {
        this.intereses = intereses;
    }
}

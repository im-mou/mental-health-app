package com.proyectosm.mentalhealthapp.ui.settings;

public class InterestsModel {
    public Integer id;
    public String title;
    public boolean active;

    public InterestsModel(Integer id, String title, boolean active) {
        this.id = id;
        this.title = title;
        this.active = active;
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

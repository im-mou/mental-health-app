package com.proyectosm.mentalhealthapp;

import com.proyectosm.mentalhealthapp.ui.dashboard.JournalModel;

public class UserModel2 {
    int id;
    String token,name, sleep_time;
    int notifications;

    public UserModel2(int id, String token, String name, String sleep_time, int notifications) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.sleep_time = sleep_time;
        this.notifications = notifications;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSleep_time() {
        return sleep_time;
    }

    public void setSleep_time(String sleep_time) {
        this.sleep_time = sleep_time;
    }

    public int isNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }
}

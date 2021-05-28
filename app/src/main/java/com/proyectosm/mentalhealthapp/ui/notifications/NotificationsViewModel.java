package com.proyectosm.mentalhealthapp.ui.notifications;

import com.proyectosm.mentalhealthapp.DatesUtils;
import com.proyectosm.mentalhealthapp.ui.dashboard.JournalModel;

import java.util.Date;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> current_date;
    private MutableLiveData<JournalModel[]> journalJsonData;

    public NotificationsViewModel() {
        current_date = new MutableLiveData<>();
        journalJsonData = new MutableLiveData<>();

        current_date.setValue(new DatesUtils(new Date()).getCurrentDate());
    }

    public LiveData<String> getCurrentDate() {
        return current_date;
    }
    public LiveData<JournalModel[]> getJournalJsonData() {
        return journalJsonData;
    }

    public void setJournalJsonData(JournalModel[] data) {
        journalJsonData.setValue(data);
    }
}
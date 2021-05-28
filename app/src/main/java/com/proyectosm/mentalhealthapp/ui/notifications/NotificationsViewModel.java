package com.proyectosm.mentalhealthapp.ui.notifications;

import com.proyectosm.mentalhealthapp.DatesUtils;

import java.util.Date;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> current_date;
    private MutableLiveData<NotificationsFragment.JournalModel[]> journalJsonData;

    public NotificationsViewModel() {
        current_date = new MutableLiveData<>();
        journalJsonData = new MutableLiveData<>();

        current_date.setValue(new DatesUtils(new Date()).getCurrentDate());
    }

    public LiveData<String> getCurrentDate() {
        return current_date;
    }
    public LiveData<NotificationsFragment.JournalModel[]> getJournalJsonData() {
        return journalJsonData;
    }

    public void setJournalJsonData(NotificationsFragment.JournalModel[] data) {
        journalJsonData.setValue(data);
    }
}
package com.proyectosm.mentalhealthapp.ui.notifications;

import com.proyectosm.mentalhealthapp.DatesUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> current_date;
    private MutableLiveData<CalendarModel[]> calendarJsonData;

    public NotificationsViewModel() {
        current_date = new MutableLiveData<>();
        calendarJsonData = new MutableLiveData<>();

        current_date.setValue(new DatesUtils().getCurrentDate());
    }

    public LiveData<String> getCurrentDate() {
        return current_date;
    }
    public LiveData<CalendarModel[]> getCalendarJsonData() {
        return calendarJsonData;
    }

    public void setCalendarJsonData(CalendarModel[] data) {
        calendarJsonData.setValue(data);
    }
}
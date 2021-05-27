package com.proyectosm.mentalhealthapp.ui.initialconfig;

import com.proyectosm.mentalhealthapp.ui.settings.InterestsModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InitialconfigModel extends ViewModel {

    private MutableLiveData<InterestsModel[]> minterests;

    public void setInterests(InterestsModel[] interests) {
        minterests.setValue(interests);
    }

    public InitialconfigModel() {
        minterests = new MutableLiveData<>();
    }

    public LiveData<InterestsModel[]> getInterests() {
        return minterests;
    }
}
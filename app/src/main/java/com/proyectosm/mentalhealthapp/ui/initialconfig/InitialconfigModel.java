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

    public void setMinterests(MutableLiveData<InterestsModel[]> minterests) {
        this.minterests = minterests;
    }

    public InitialconfigModel() {
        minterests = new MutableLiveData<>();
    }

    public LiveData<InterestsModel[]> getInterests() {
        return minterests;
    }

    // obtenemos una cadena con las ids de los intereses -> "1|3|9|..."
    public String getActiveInterests() {

        String activeInterests = "";
        InterestsModel[] interests = minterests.getValue();

        for (int i = 0; i < interests.length; i++) {
            if (interests[i].isActive()){
                activeInterests += "|" + Integer.toString(interests[i].getId());
            }
        }

        // eliminamos el primer pipe
        if (activeInterests == "") {
            return "";
        } else {
            return activeInterests.substring(1);
        }
    }

}
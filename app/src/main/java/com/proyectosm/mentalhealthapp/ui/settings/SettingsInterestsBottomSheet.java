package com.proyectosm.mentalhealthapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.proyectosm.mentalhealthapp.R;

import java.util.ArrayList;

public class SettingsInterestsBottomSheet extends BottomSheetDialogFragment {

    private InterestsModel interests[] = {
            new InterestsModel(1,"Interes 1", 0.5,false),
            new InterestsModel(2,"Interes 2", 0.5,false),
            new InterestsModel(3,"Interes 3", 0.5,true),
            new InterestsModel(4,"Interes 4", 0.5,false),
            new InterestsModel(5,"Interes 5", 0.5,true),
            new InterestsModel(6,"Interes 6", 0.5,true),
            new InterestsModel(7,"Interes 7", 0.5,false),
            new InterestsModel(8,"Interes 8", 0.5,false),
    };

    public SettingsInterestsBottomSheet() {
        super();
        // obtener la lista de los intereses desdel servidor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View sheetView = inflater.inflate(R.layout.bottom_sheet_intereses, null, false);


        ListView listView = (ListView) sheetView.findViewById(R.id.my_interests_list);

        // Construct the data source
        ArrayList<InterestsModel> arrayOfInterestsEntries = new ArrayList<InterestsModel>();

        // For populating list data
        InterestsListAdapter interestsListAdapter = new InterestsListAdapter(getActivity(), arrayOfInterestsEntries);
        listView.setAdapter(interestsListAdapter);

        interestsListAdapter.addAll(interests);

        return sheetView;
    }
}

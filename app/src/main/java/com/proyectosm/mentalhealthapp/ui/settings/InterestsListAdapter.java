package com.proyectosm.mentalhealthapp.ui.settings;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.proyectosm.mentalhealthapp.R;

import java.util.ArrayList;

public class InterestsListAdapter extends ArrayAdapter<InterestsModel> {
    private Activity context;
    private ArrayList<InterestsModel> interests;

    public InterestsListAdapter(Activity context, ArrayList<InterestsModel> interests) {
        super(context, R.layout.interest_list_item, interests);
        this.context = context;
        this.interests = interests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        InterestsModel interest = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.interest_list_item, null, false);
        }

        // Lookup view for data population
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.interest_listitem_title);
        CheckBox checkboxAnswer = (CheckBox) convertView.findViewById(R.id.interest_listitem_checked);

        // Populate the data into the template view using the data object
        textViewTitle.setText(interest.title);
        checkboxAnswer.setChecked(interest.active);

        // Return the completed view to render on screen
        return convertView;

    }


}
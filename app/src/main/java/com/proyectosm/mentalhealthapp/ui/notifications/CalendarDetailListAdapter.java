package com.proyectosm.mentalhealthapp.ui.notifications;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.proyectosm.mentalhealthapp.R;
import com.proyectosm.mentalhealthapp.ui.dashboard.JournalModel;

import java.util.ArrayList;

public class CalendarDetailListAdapter extends ArrayAdapter<JournalModel> {
    private Activity context;
    private ArrayList<JournalModel> journal;

    public CalendarDetailListAdapter(Activity context, ArrayList<JournalModel> journal) {
        super(context, R.layout.calendar_details_list_item, journal);
        this.context = context;
        this.journal = journal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        JournalModel journal = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.calendar_details_list_item, null, false);
        }

        // Lookup view for data population
        TextView textViewQuestion = (TextView) convertView.findViewById(R.id.cal_detail_item_title);
        TextView textViewAnswer = (TextView) convertView.findViewById(R.id.cal_detail_item_text);

        // Populate the data into the template view using the data object
        textViewQuestion.setText(journal.question);
        textViewAnswer.setText(journal.answer);


        // Return the completed view to render on screen
        return convertView;

    }


}
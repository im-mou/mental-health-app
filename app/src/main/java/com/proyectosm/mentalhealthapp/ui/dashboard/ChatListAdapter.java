package com.proyectosm.mentalhealthapp.ui.dashboard;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.proyectosm.mentalhealthapp.R;

import java.util.ArrayList;

public class ChatListAdapter extends ArrayAdapter<ChatModel> {
    private Activity context;
    private ArrayList<ChatModel> journals;

    public ChatListAdapter(Activity context, ArrayList<ChatModel> journals) {
        super(context, R.layout.interest_list_item, journals);
        this.context = context;
        this.journals = journals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        ChatModel chatModel = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            if(chatModel.left) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_left_item, null, false);
            } else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_right_item, null, false);
            }
        }

        // Lookup view for data population
        TextView textViewMessage = (TextView) convertView.findViewById(R.id.chat_item);

        // Populate the data into the template view using the data object
        textViewMessage.setText(chatModel.text);

        // Return the completed view to render on screen
        return convertView;

    }


}
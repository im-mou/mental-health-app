package com.proyectosm.mentalhealthapp.ui.notifications;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.proyectosm.mentalhealthapp.R;

import androidx.core.content.ContextCompat;

public class CalendarListAdapter extends ArrayAdapter {
    private Integer[] dates;
    private int[] sentiment_color;
    private Activity context;
    private int currentday;


    public CalendarListAdapter(Activity context, Integer[] dates, int[] sentiment_color, int currentday) {
        super(context, R.layout.calendar_list_item, dates);
        this.context = context;
        this.dates = dates;
        this.sentiment_color = sentiment_color;
        this.currentday = currentday;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView==null)
            row = inflater.inflate(R.layout.calendar_list_item, parent, true);

        TextView textViewDate = (TextView) row.findViewById(R.id.cal_date);
        TextView textViewColor = (TextView) row.findViewById(R.id.cal_color);

        textViewDate.setText(dates[position].toString());
        textViewColor.setBackgroundColor(ContextCompat.getColor(this.context, sentiment_color[position]));

        if(dates[position] == this.currentday){
            row.setBackground(ContextCompat.getDrawable(this.context, R.drawable.calendar_color_rectangle));
        }

        return row;
    }
}
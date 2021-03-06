package com.proyectosm.mentalhealthapp.ui.notifications;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.proyectosm.mentalhealthapp.DatesUtils;
import com.proyectosm.mentalhealthapp.R;

import java.time.LocalDate;
import java.util.ArrayList;

import androidx.core.content.ContextCompat;

public class CalendarListAdapter extends ArrayAdapter<CalendarModel> {
    private Activity context;
    private ArrayList<CalendarModel> calendar;

    // Función para los datos del calendario general
    public CalendarListAdapter(Activity context, ArrayList<CalendarModel> calendar) {
        super(context, R.layout.calendar_list_item, calendar);
        this.context = context;
        this.calendar = calendar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CalendarModel calendar = getItem(position);

        // Mira si se está reusando una vista, sino llama a la función inflate
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.calendar_list_item, null, false);
        }

        // Enlaza los botones locales a los items del layout
        TextView textViewDate = (TextView) convertView.findViewById(R.id.cal_date);
        TextView textViewColor = (TextView) convertView.findViewById(R.id.cal_color);

        // Se recupera el día actual y se escribe en el box textViewDate junto al color
        String dateDay = new DatesUtils(calendar.date).getDay();
        textViewDate.setText(String.valueOf(dateDay));
        textViewColor.setBackgroundColor(Color.parseColor(calendar.color));

        // Remarca la entrada 'hoy' poniéndole un marco de color
        if(Integer.parseInt(dateDay) == LocalDate.now().getDayOfMonth()) {
            convertView.setBackground(ContextCompat.getDrawable(this.context, R.drawable.calendar_color_rectangle));
        } else {
            convertView.setBackground(null);
        }

        // En función del sentiment analizado colocaría un texto
        if(calendar.sentiment_index == 0 && position + 1  < LocalDate.now().getDayOfMonth()) {
            textViewColor.setText("Vacío");
        } else {
            textViewColor.setText("{mood}");
        }

        // Devuelve la vista
        return convertView;

    }


}


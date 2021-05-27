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

    // Función para los datos más detallados del calendario (al pulsar un boton de color)
    public CalendarDetailListAdapter(Activity context, ArrayList<JournalModel> journal) {
        super(context, R.layout.calendar_details_list_item, journal);
        this.context = context;
        this.journal = journal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JournalModel journal = getItem(position);

        // Mira si se está reusando una vista, sino llama a la función inflate
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.calendar_details_list_item, null, false);
        }

        // Enlaza los botones locales a los items del layout
        TextView textViewQuestion = (TextView) convertView.findViewById(R.id.cal_detail_item_title);
        TextView textViewAnswer = (TextView) convertView.findViewById(R.id.cal_detail_item_text);

        // Rellena la lista de datos según los objetos que se le pasan
        textViewQuestion.setText(journal.question);
        textViewAnswer.setText(journal.answer);

        // Devuelve la vista
        return convertView;

    }
}
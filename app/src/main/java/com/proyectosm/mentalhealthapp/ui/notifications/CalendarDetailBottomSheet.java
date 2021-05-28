package com.proyectosm.mentalhealthapp.ui.notifications;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.proyectosm.mentalhealthapp.DatesUtils;
import com.proyectosm.mentalhealthapp.R;

import java.util.ArrayList;

public class CalendarDetailBottomSheet extends BottomSheetDialogFragment {
    // Variables iniciales para crear el objeto calendario
    private int position;
    private NotificationsFragment.JournalModel journalEntry;


    // Inicializa los datos de posición y calendario
    public CalendarDetailBottomSheet(int position, NotificationsFragment.JournalModel journalEntry) {
        super();
        this.position = position;
        this.journalEntry = journalEntry;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View sheetView = inflater.inflate(R.layout.bottom_sheet_detalle_calendario, null, false);

        // Enlaza los items del layout a variables locales
        TextView textViewDetailTitle = (TextView) sheetView.findViewById(R.id.cal_detail_sheet_title);
        TextView textViewStatus = (TextView) sheetView.findViewById(R.id.cal_details_sheet_status);

        // Guarda la fecha actual y la escribe en uno de los textos
        String humanDate = new DatesUtils(journalEntry.date).getHumanDate();
        textViewDetailTitle.setText(humanDate);

        // Se ponen textos de ejemplo en los boxes del calendario
        textViewStatus.setText("Ayer estabas {mood}");

        // El calendario toma los colores según lo que le haya dicho el usuario
        textViewStatus.setBackgroundColor(Color.parseColor(journalEntry.color));

        // Se crea la lista del calendario vacía
        ListView listView = (ListView) sheetView.findViewById(R.id.calendar_dates_list);
        ArrayList<NotificationsFragment.ChatModel> arrayOfJournalEntries = new ArrayList<NotificationsFragment.ChatModel>();

        // y se rellena de datos con las entradas que recibe
        CalendarDetailListAdapter calendarDetailListAdapter = new CalendarDetailListAdapter(getActivity(), arrayOfJournalEntries);
        listView.setAdapter(calendarDetailListAdapter);

        calendarDetailListAdapter.addAll(journalEntry.chat);

        return sheetView;
    }
}

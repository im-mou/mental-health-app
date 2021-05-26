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
import com.proyectosm.mentalhealthapp.ui.dashboard.JournalModel;

import java.util.ArrayList;

public class CalendarDetailBottomSheet extends BottomSheetDialogFragment {
    private int position;
    private CalendarModel calendarEntry;

    public CalendarDetailBottomSheet(int position, CalendarModel calendarEntry) {
        super();
        this.position = position;
        this.calendarEntry = calendarEntry;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View sheetView = inflater.inflate(R.layout.bottom_sheet_detalle_calendario, null, false);


        TextView textViewDetailTitle = (TextView) sheetView.findViewById(R.id.cal_detail_sheet_title);
        TextView textViewStatus = (TextView) sheetView.findViewById(R.id.cal_details_sheet_status);
//        TextView textViewPreheader = (TextView) sheetView.findViewById(R.id.cal_detail_sheet_preheader);

        String humanDate = new DatesUtils(this.calendarEntry.date).getHumanDate();
        textViewDetailTitle.setText(humanDate);

        textViewStatus.setText("Ayer estuviste to loco");
        textViewStatus.setBackgroundColor(Color.parseColor(this.calendarEntry.color));

//        textViewPreheader.setTextColor(Color.parseColor(this.calendarEntry.color));


        ListView listView = (ListView) sheetView.findViewById(R.id.calendar_dates_list);
        // Construct the data source
        ArrayList<JournalModel> arrayOfJournalEntries = new ArrayList<JournalModel>();

        // For populating list data
        CalendarDetailListAdapter calendarDetailListAdapter = new CalendarDetailListAdapter(getActivity(), arrayOfJournalEntries);
        listView.setAdapter(calendarDetailListAdapter);

        calendarDetailListAdapter.addAll(this.calendarEntry.journal);

        return sheetView;
    }
}

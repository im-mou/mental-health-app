package com.proyectosm.mentalhealthapp.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.proyectosm.mentalhealthapp.DatesUtils;
import com.proyectosm.mentalhealthapp.databinding.FragmentNotificationsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final RecyclerView recyclerView = binding.calendarDatesList;
        final TextView title_month = binding.calTitleDate;
        final TextView title_today_date = binding.calTodayDate;
        final ImageButton button_left = binding.calBtnLeft;
        final ImageButton button_right = binding.calBtnRight;


        // Construct the data source
        CalendarRecyclerAdapter calendarRecyclerAdapter = new CalendarRecyclerAdapter(getContext(), getCalendarData(notificationsViewModel.getCurrentDate().getValue()));
        recyclerView.setAdapter(calendarRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // observar a los cambios de la fecha
        notificationsViewModel.getCurrentDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String changeDate) {
                DatesUtils parsedDate = new DatesUtils(changeDate);
                title_month.setText(parsedDate.getMonth() + ", " + parsedDate.getYear());
                title_today_date.setText("Hoy es " + parsedDate.getHumanDate());

                CalendarModel[] jsonData = getCalendarData(notificationsViewModel.getCurrentDate().getValue());
                notificationsViewModel.setCalendarJsonData(jsonData);
            }
        });

        // observar a los cambios de la estructura json de la lista de calendario
        notificationsViewModel.getCalendarJsonData().observe(getViewLifecycleOwner(), new Observer<CalendarModel[]>() {
            @Override
            public void onChanged(@Nullable CalendarModel[] jsonData) {
                // For populating list data
//                calendarRecyclerAdapter.clear();
//                calendarRecyclerAdapter.addAll(jsonData);
            }
        });

        button_left.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private CalendarModel[] getCalendarData(String date){

        // hacer la llamada API al servidor para obtener los datos
        String calendarModelsData = "[{ 'date': '01-05-2021', 'sentiment_index': 1.0, 'color': '#FFFF8078', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '02-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '03-05-2021', 'sentiment_index': 1.0, 'color': '#FF7FECB7', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '04-05-2021', 'sentiment_index': 1.0, 'color': '#FFFF8078', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '05-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '06-05-2021', 'sentiment_index': 1.0, 'color': '#FFFF8078', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '07-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '08-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '09-05-2021', 'sentiment_index': 1.0, 'color': '#FFFF8078', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '10-05-2021', 'sentiment_index': 1.0, 'color': '#FFFF8078', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '11-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '12-05-2021', 'sentiment_index': 1.0, 'color': '#FFFF8078', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '13-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '14-05-2021', 'sentiment_index': 1.0, 'color': '#FFFF8078', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '15-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '16-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '17-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '18-05-2021', 'sentiment_index': 1.0, 'color': '#FF7FECB7', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '19-05-2021', 'sentiment_index': 1.0, 'color': '#FFFF8078', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '20-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '21-05-2021', 'sentiment_index': 1.0, 'color': '#FFFF8078', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '22-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '23-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '24-05-2021', 'sentiment_index': 1.0, 'color': '#FF7FECB7', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '25-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '26-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '27-05-2021', 'sentiment_index': 1.0, 'color': '#FFFF8078', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '28-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFD978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '29-05-2021', 'sentiment_index': 1.0, 'color': '#FF7FECB7', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] },"
                + "{ 'date': '30-05-2021', 'sentiment_index': 1.0, 'color': '#FFFFB978', 'journal': [{'question': 'question 1', 'answer': 'answer 1'}, {'question': 'question 2', 'answer': 'answer 2'}] }]";

        //ArrayList<CalendarModel> newUsers = CalendarModel.fromJson(jsonArray)
        Gson gson = new Gson();
        CalendarModel[] jsonData = gson.fromJson(calendarModelsData, CalendarModel[].class);

        return jsonData;
    }
}
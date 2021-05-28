package com.proyectosm.mentalhealthapp.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.proyectosm.mentalhealthapp.DatesUtils;
import com.proyectosm.mentalhealthapp.R;
import com.proyectosm.mentalhealthapp.databinding.FragmentNotificationsBinding;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private ListView listView;
    OkHttpClient client;

    public class ChatModel {
        int journal_id;
        int type;
        String body;

        public ChatModel(int journal_id, int type, String body) {
            this.journal_id = journal_id;
            this.type = type;
            this.body = body;
        }
    }

    public class JournalModel {
        int journal_id;
        int user_id;
        String date;
        String color;
        double sentiment_index;
        ChatModel[] chat;

        public JournalModel(int journal_id, int user_id, String date, String color, double sentiment_index, ChatModel[] chat) {
            this.journal_id = journal_id;
            this.user_id = user_id;
            this.date = date;
            this.color = color;
            this.sentiment_index = sentiment_index;
            this.chat = chat;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        client = new OkHttpClient();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        final RecyclerView recyclerView = binding.calendarDatesList;
        final TextView title_month = binding.calTitleDate;
        final TextView title_today_date = binding.calTodayDate;
        final ImageButton button_left = binding.calBtnLeft;
        final ImageButton button_right = binding.calBtnRight;


        // Construct the data source
        CalendarRecyclerAdapter calendarRecyclerAdapter = new CalendarRecyclerAdapter(getContext(), getCurrentJournalData());
        recyclerView.setAdapter(calendarRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // observar a los cambios de la fecha
        notificationsViewModel.getCurrentDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String changeDate) {
                DatesUtils parsedDate = new DatesUtils(changeDate);
                title_month.setText(parsedDate.getMonth() + ", " + parsedDate.getYear());
                title_today_date.setText("Hoy es " + parsedDate.getHumanDate());
//
//                JournalModel[] jsonData = getCalendarData(notificationsViewModel.getCurrentDate().getValue());
//                notificationsViewModel.setJournalJsonData(jsonData);
            }
        });

        // observar a los cambios de la estructura json de la lista de calendario
        notificationsViewModel.getJournalJsonData().observe(getViewLifecycleOwner(), new Observer<JournalModel[]>() {
            @Override
            public void onChanged(@Nullable JournalModel[] jsonData) {
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

    private JournalModel[] getCurrentJournalData(){

        JournalModel[] journalModels = null;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Integer month = localDate.getMonthValue();
        Integer year = localDate.getYear();

        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("month", String.format("%02d", month))
                .add("year",  Integer.toString(year))
                .build();

        Request request = new Request.Builder()
                .url(getResources().getString(R.string.api_url)+"/journal/month")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new Gson();
            journalModels = gson.fromJson(response.body().string(), JournalModel[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return journalModels;
    }


    private JournalModel[] getCalendarData(String date){

        JournalModel[] journalModels = null;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");


        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("date", date)
                .build();

        Request request = new Request.Builder()
                .url(getResources().getString(R.string.api_url)+"/journal")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new Gson();
            journalModels = gson.fromJson(response.body().string(), JournalModel[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return journalModels;
    }

}
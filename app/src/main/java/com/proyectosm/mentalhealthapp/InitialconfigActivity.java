package com.proyectosm.mentalhealthapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.proyectosm.mentalhealthapp.ui.settings.InterestsListAdapter;
import com.proyectosm.mentalhealthapp.ui.settings.InterestsModel;

import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InitialconfigActivity extends AppCompatActivity {

    private InterestsModel interests[] = {
            new InterestsModel(1,"Interes 1", false),
            new InterestsModel(2,"Interes 2", false),
            new InterestsModel(3,"Interes 3", true),
            new InterestsModel(4,"Interes 4", false),
            new InterestsModel(5,"Interes 5", true),
            new InterestsModel(6,"Interes 6", true),
            new InterestsModel(7,"Interes 7", false),
            new InterestsModel(8,"Interes 8", false),
    };

    OkHttpClient client;
    Button startBtn;
    TextInputLayout nameTextVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.initialconfig_view);

        ListView listView = (ListView) this.findViewById(R.id.initial_interest_list);

        // Construct the data source
        ArrayList<InterestsModel> arrayOfInterestsEntries = new ArrayList<InterestsModel>();

        // meter los datos a la lista de los ntereses
        InterestsListAdapter interestsListAdapter = new InterestsListAdapter(this, arrayOfInterestsEntries);
        listView.setAdapter(interestsListAdapter);
        interestsListAdapter.addAll(interests);

        startBtn = (Button) this.findViewById(R.id.initial_start_button);
        nameTextVIew = (TextInputLayout) this.findViewById(R.id.initial_name);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postUserData();
            }
        });

    }

    public void postUserData() {

        client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("name", "holaaaa")
                .add("interest", "1|2|5")
                .build();

        Request request = new Request.Builder()
                .url(getResources().getString(R.string.api_url)+"/user/register")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());


            Gson gson = new Gson();
            TokenModel jsonData = gson.fromJson(response.body().string(), TokenModel.class);

            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token", jsonData.getToken());

            nameTextVIew.getEditText().setText(jsonData.getToken());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
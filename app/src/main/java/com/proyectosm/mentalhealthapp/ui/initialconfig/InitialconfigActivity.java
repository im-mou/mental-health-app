package com.proyectosm.mentalhealthapp.ui.initialconfig;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.proyectosm.mentalhealthapp.MainActivity;
import com.proyectosm.mentalhealthapp.R;
import com.proyectosm.mentalhealthapp.TokenModel;
import com.proyectosm.mentalhealthapp.ui.settings.InterestsListAdapter;
import com.proyectosm.mentalhealthapp.ui.settings.InterestsModel;

import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InitialconfigActivity extends AppCompatActivity {

    InitialconfigModel initialconfigModel;

    private InterestsModel[] interests;

    OkHttpClient client;
    Button startBtn;
    TextInputLayout nameTextVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new OkHttpClient();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initialconfigModel = new ViewModelProvider(this).get(InitialconfigModel.class);

        setContentView(R.layout.initialconfig_view);

        startBtn = (Button) this.findViewById(R.id.initial_start_button);
        nameTextVIew = (TextInputLayout) this.findViewById(R.id.initial_name);
        ListView listView = (ListView) this.findViewById(R.id.initial_interest_list);

        // Construct the data source
        ArrayList<InterestsModel> arrayOfInterestsEntries = new ArrayList<InterestsModel>();

        // meter los datos a la lista de los intereses
        InterestsListAdapter interestsListAdapter = new InterestsListAdapter(this, arrayOfInterestsEntries);
        listView.setAdapter(interestsListAdapter);

        initialconfigModel.setInterests(getInterests());


        // usamos un observador para actualizar la lista
        initialconfigModel.getInterests().observe(this, new Observer<InterestsModel[]>() {
            @Override
            public void onChanged(InterestsModel[] data) {
                interestsListAdapter.clear();
                interestsListAdapter.addAll(data);
            }
        });

        // colocamos un listener para detectar los clicks a los intereses
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // al hacer click cambiamos el estado del a "active|deactive"
                InterestsModel[] im = initialconfigModel.getInterests().getValue();
                im[position].setActive(!im[position].isActive());

                initialconfigModel.setInterests(im);
                interestsListAdapter.notifyDataSetChanged();
            }
        });

        // escuchamos el click al boton del "empezar"
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String activeInterests = initialconfigModel.getActiveInterests();

                RequestBody formBody = new FormBody.Builder()
                        .add("name", nameTextVIew.getEditText().getText().toString().trim())
                        .add("interest", activeInterests)
                        .build();

                Request request = new Request.Builder()
                        .url(getResources().getString(R.string.api_url)+"/user/register")
                        .post(formBody)
                        .build();


                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Gson gson = new Gson();
                    TokenModel jsonData = gson.fromJson(response.body().string(), TokenModel.class);

                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", jsonData.getToken());
                    editor.apply();

                    Intent intent = new Intent(InitialconfigActivity.this, MainActivity.class);
                    startActivity(intent);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private InterestsModel[] getInterests(){

        InterestsModel[] interestsModels = null;

        Request request = new Request.Builder()
                .url(getResources().getString(R.string.api_url)+"/interests")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new Gson();
            interestsModels = gson.fromJson(response.body().string(), InterestsModel[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return interestsModels;
    }

}
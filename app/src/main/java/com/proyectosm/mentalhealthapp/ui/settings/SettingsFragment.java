package com.proyectosm.mentalhealthapp.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.proyectosm.mentalhealthapp.R;
import com.proyectosm.mentalhealthapp.UserModel2;
import com.proyectosm.mentalhealthapp.databinding.FragmentSettingsBinding;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;
    OkHttpClient client;

    public class UserInfoModel {
        UserModel2 user;
        InterestsModel[] interests;

        public UserInfoModel(UserModel2 user, InterestsModel[] interests) {
            this.user = user;
            this.interests = interests;
        }

        public UserModel2 getUser() {
            return user;
        }

        public void setUser(UserModel2 user) {
            this.user = user;
        }

        public InterestsModel[] getInterests() {
            return interests;
        }

        public void setInterests(InterestsModel[] interests) {
            this.interests = interests;
        }
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        client = new OkHttpClient();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        final TextInputLayout name = binding.settingsInputChangeName;
        final TextInputLayout sleepHours = binding.settingsInputChangeSleepHour;
        final Button disableNotifications = binding.settingBtnDisableNotifications;
        final Button editInterests = binding.settingsBtnEditInterests;



        editInterests.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsInterestsBottomSheet sheet = new SettingsInterestsBottomSheet();
                sheet.show(getActivity().getSupportFragmentManager(), "calender_detail_sheet");
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "defaultValue");

        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .build();

        Request request = new Request.Builder()
                .url(getResources().getString(R.string.api_url)+"/user/info")
                .post(formBody)
                .build();


        // Hacemos una peticion al servidor cloud para registrar el usuario
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            Gson gson = new Gson();
            UserInfoModel jsonData = gson.fromJson(response.body().string(), UserInfoModel.class);

            name.getEditText().setText(jsonData.getUser().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }



        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
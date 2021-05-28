package com.proyectosm.mentalhealthapp.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.proyectosm.mentalhealthapp.R;
import com.proyectosm.mentalhealthapp.UserModel2;
import com.proyectosm.mentalhealthapp.databinding.FragmentSettingsBinding;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.proyectosm.mentalhealthapp.MainActivity.setNotifications;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;
    OkHttpClient client;
    UserModel2 currentUser;
    InterestsModel[] userInterests;

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
        final Button guardarDatos = binding.settingsBtnSave;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        guardarDatos.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tmpName = name.getEditText().getText().toString();
                String tmpSleep = sleepHours.getEditText().getText().toString();

                boolean nombreBien = true;
                if (tmpName.length() == 0) {
                    nombreBien = false;
                    Toast.makeText(getContext(), "El nombre está vacío", Toast.LENGTH_SHORT).show();
                }

                boolean fechaBien = false;
                if (tmpSleep.length() == 5) {
                    if (tmpSleep.charAt(0) >= '0' && tmpSleep.charAt(0) <= '2') {
                        if (tmpSleep.charAt(1) >= '0' && tmpSleep.charAt(1) <= '4') {
                            if (tmpSleep.charAt(2) == ':') {
                                if (tmpSleep.charAt(3) >= '0' && tmpSleep.charAt(3) <= '5') {
                                    if (tmpSleep.charAt(4) >= '0' && tmpSleep.charAt(4) <= '9') {
                                        fechaBien = true;
                                    }
                                }
                            }
                        }
                    }
                }


                if (fechaBien) {
                    if (nombreBien) {
                        RequestBody formBody = new FormBody.Builder()
                                .add("token", token)
                                .add("name", tmpName)
                                .add("sleep_time", tmpSleep)
                                .build();

                        Request request = new Request.Builder()
                                .url(getResources().getString(R.string.api_url)+"/user/update")
                                .post(formBody)
                                .build();


                            // Hacemos una peticion al servidor cloud para registrar el usuario
                        try (Response response = client.newCall(request).execute()) {
                            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                            Toast.makeText(getContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
                            getUserinfo(token);

                            if(currentUser.isNotifications() == 1) {
                                Toast.makeText(getContext(), currentUser.getSleep_time().substring(0, 2), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getContext(), currentUser.getSleep_time().substring(3, 5), Toast.LENGTH_SHORT).show();

                                setNotifications(Integer.parseInt(currentUser.getSleep_time().substring(0, 2)), Integer.parseInt(currentUser.getSleep_time().substring(3, 5)), 55, true, getContext());
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "La fecha está mal", Toast.LENGTH_SHORT).show();
                }
            }
        });

        disableNotifications.setOnClickListener(new AdapterView.OnClickListener() {
           @Override
           public void onClick(View v){
               RequestBody formBody = new FormBody.Builder()
                       .add("token", token)
                       .build();

               try {
                   Request request = new Request.Builder()
                           .url(getResources().getString(R.string.api_url)+"/user/togglenotifications")
                           .post(formBody)
                           .build();

                   try (Response response = client.newCall(request).execute()) {
                       if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                       if(currentUser != null){

                           boolean toggle;

                           if(currentUser.isNotifications() == 1){
                               currentUser.setNotifications(0);
                               disableNotifications.setText("Activar notificaciones");
                               Toast.makeText(getContext(), "Notificaciones desactivadas", Toast.LENGTH_SHORT).show();
                               toggle = false;

                           }
                           else {
                               currentUser.setNotifications(1);
                               disableNotifications.setText("Desactivar notificaciones");
                               Toast.makeText(getContext(), "Notificaciones activadas", Toast.LENGTH_SHORT).show();
                               toggle = true;
                           }


                           Toast.makeText(getContext(), currentUser.getSleep_time(), Toast.LENGTH_SHORT).show();
                           setNotifications(Integer.parseInt(currentUser.getSleep_time().substring(0, 2)), Integer.parseInt(currentUser.getSleep_time().substring(3, 5)), 55, toggle, getContext());

                       }
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

               } catch (Exception e) {
                   Toast.makeText(getContext(), "Error. No se ha podido actualizar!", Toast.LENGTH_SHORT).show();
               }
           }
        });

        editInterests.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsInterestsBottomSheet sheet = new SettingsInterestsBottomSheet();
                sheet.show(getActivity().getSupportFragmentManager(), "calender_detail_sheet");
            }
        });



        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .build();

        Request request = new Request.Builder()
                .url(getResources().getString(R.string.api_url)+"/user/info")
                .post(formBody)
                .build();


        // Hacemos una peticion al servidor cloud para registrar el usuario
        getUserinfo(token);

        name.getEditText().setText(currentUser.getName());
        sleepHours.getEditText().setText(currentUser.getSleep_time());


        if(currentUser.isNotifications() == 1){
            disableNotifications.setText("Desactivar notificaciones");
        }
        else {
            disableNotifications.setText("Activar notificaciones");
        }

        return root;
    }

    public void getUserinfo(String token) {
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .build();

        Request request = new Request.Builder()
                .url(getResources().getString(R.string.api_url)+"/user/info")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            Gson gson = new Gson();
            UserInfoModel jsonData = gson.fromJson(response.body().string(), UserInfoModel.class);

            currentUser = jsonData.getUser();
            userInterests = jsonData.getInterests();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
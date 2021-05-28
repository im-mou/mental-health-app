package com.proyectosm.mentalhealthapp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.proyectosm.mentalhealthapp.R;
import com.proyectosm.mentalhealthapp.UserModel2;
import com.proyectosm.mentalhealthapp.databinding.FragmentHomeBinding;
import com.proyectosm.mentalhealthapp.ui.settings.SettingsFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    // Variables principales de la vista del home
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ListView listView;

    // Actividades de ejemplo, sus descripciones e iconos
    private String rec_title[] = {
            "Netflix",
            "Meditación",
    };

    private String rec_description[] = {
            "¿Qué tal si descansas un poco y ves una de estas series...?\n",
            "Estas técnicas de meditación seguro que te ayudan a calmarte...",
    };

    private Uri rec_images[] = {
            Uri.parse("https://nementio.com/wp-content/uploads/netflix-new-icon.png"),
            Uri.parse("https://lh3.googleusercontent.com/proxy/A7YjlD6bQYMIKuRzIonnq2C-5d2VssmsgbeuhPKlrVtlrZFztOxyZRWRWTNIi29hZB1YLQiVYqlW-s7WlSJT-fxNNjmFHn4arBHA"),
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = (ListView) root.findViewById(R.id.recomendation_list);

        // Rellena la lista de los items de ejemplo creados arriba
        RecomendationListAdapter recomendationList = new RecomendationListAdapter(getActivity(), rec_title, rec_description, rec_images);
        listView.setAdapter(recomendationList);

        OkHttpClient client = new OkHttpClient();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // en esta parte comprobamos si existe el token para identificar al usuairio,
        // En el caso de que no exista el token, redirigimos al usuairo a la paginas para el registro
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if(token != "") {
            RequestBody formBody = new FormBody.Builder()
                    .add("token", token)
                    .build();

            getTitleAndRecomendations(client, formBody, root);
        }

        return root;
    }

    private void getTitleAndRecomendations(OkHttpClient client, RequestBody formBody, View view){
        Request request = new Request.Builder()
                .url(getResources().getString(R.string.api_url)+"/user/info")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            UserModel2 currentUser;

            Gson gson = new Gson();
            assert response.body() != null;
            SettingsFragment.UserInfoModel jsonData = gson.fromJson(response.body().string(), SettingsFragment.UserInfoModel.class);

            currentUser = jsonData.getUser();


            TextView pretitle = (TextView)view.findViewById(R.id.bubble_pretitle);

            pretitle.setText(getString(R.string.hello) + " " + currentUser.getName());
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
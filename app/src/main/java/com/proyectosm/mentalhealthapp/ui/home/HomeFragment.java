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

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    public class Rec_list {
        String titulo;
        String description;
        Uri url;

        public Rec_list(String titulo, String desc, Uri icon) {
            this.titulo = titulo;
            this.description = desc;
            this.url = icon;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Uri getIcon() {
            return url;
        }

        public void setIcon(Uri icon) {
            this.url = icon;
        }
    }


    // Variables principales de la vista del home
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ListView listView;

    // Actividades de ejemplo, sus descripciones e iconos
    private String rec_title[] = {
            "Netflix",
            "Meditaci??n",
    };

    private String rec_description[] = {
            "??Qu?? tal si descansas un poco y ves una de estas series...?\n",
            "Estas t??cnicas de meditaci??n seguro que te ayudan a calmarte...",
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

        OkHttpClient client = new OkHttpClient();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // en esta parte comprobamos si existe el token para identificar al usuairio,
        // En el caso de que no exista el token, redirigimos al usuairo a la paginas para el registro
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");


        if(token != "") {

            // hacemos una llamada al servidor para obtener datos del usuarios
            RequestBody formBody = new FormBody.Builder()
                    .add("token", token)
                    .build();

            Request request = new Request.Builder()
                    .url(getResources().getString(R.string.api_url) + "/user/info")
                    .post(formBody)
                    .build();


            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                UserModel2 currentUser;

                Gson gson = new Gson();
                assert response.body() != null;
                SettingsFragment.UserInfoModel jsonData = gson.fromJson(response.body().string(), SettingsFragment.UserInfoModel.class);

                currentUser = jsonData.getUser();

                TextView pretitle = (TextView) root.findViewById(R.id.bubble_pretitle);

                // pretitle.setText(getString(R.string.hello) + " " + currentUser.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }

            request = new Request.Builder()
                    .url(getResources().getString(R.string.api_url) + "/recomendations")
                    .post(formBody)
                    .build();


            // hacemos una llamada al servidor para obtener las recomendaciones
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                // Se crea la lista del calendario vac??a
                ArrayList<Rec_list> arrayOfrecomendations = new ArrayList<Rec_list>();
                RecomendationListAdapter recomendationListAdapter = new RecomendationListAdapter(getActivity(), arrayOfrecomendations);

                Gson gson = new Gson();
                assert response.body() != null;
                ArrayList<Rec_list> jsonData = gson.fromJson(response.body().string(), ArrayList.class);

                listView.setAdapter(recomendationListAdapter);
                recomendationListAdapter.addAll(jsonData);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
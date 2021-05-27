package com.proyectosm.mentalhealthapp.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.proyectosm.mentalhealthapp.R;
import com.proyectosm.mentalhealthapp.databinding.FragmentHomeBinding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class HomeFragment extends Fragment {

    // Variables principales de la vista del home
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ListView listView;

    // Actividades de ejemplo, sus descripciones e iconos
    private String rec_title[] = {
            "Netflix",
            "Meditación",
            "Haz un poco de ejercicio",
    };

    private String rec_description[] = {
            "¿Qué tal si descansas un poco y ves una de estas series...?\n",
            "Estas técnicas de meditación seguro que te ayudan a calmarte...",
            "Hacer ejercicio te puede relajar",
    };

    private Uri rec_images[] = {
            Uri.parse("https://nementio.com/wp-content/uploads/netflix-new-icon.png"),
            Uri.parse("https://lh3.googleusercontent.com/proxy/A7YjlD6bQYMIKuRzIonnq2C-5d2VssmsgbeuhPKlrVtlrZFztOxyZRWRWTNIi29hZB1YLQiVYqlW-s7WlSJT-fxNNjmFHn4arBHA"),
            Uri.parse("https://media.istockphoto.com/vectors/heart-isometric-health-care-concept-red-shape-and-heartbeat-vector-id1183325543?k=6&m=1183325543&s=612x612&w=0&h=DT5m64-MJl2mJto4eBd9UiWVR_52h9PI5E9QB7nMGa0="),
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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
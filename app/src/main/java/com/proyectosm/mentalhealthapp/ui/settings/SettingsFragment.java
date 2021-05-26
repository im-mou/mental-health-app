package com.proyectosm.mentalhealthapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.proyectosm.mentalhealthapp.databinding.FragmentSettingsBinding;
import com.proyectosm.mentalhealthapp.ui.notifications.CalendarDetailBottomSheet;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button editInterests = (Button) binding.settingsBtnEditInterests;

        editInterests.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsInterestsBottomSheet sheet = new SettingsInterestsBottomSheet();
                sheet.show(getActivity().getSupportFragmentManager(), "calender_detail_sheet");
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
package com.proyectosm.mentalhealthapp.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.proyectosm.mentalhealthapp.R;
import com.proyectosm.mentalhealthapp.databinding.FragmentNotificationsBinding;

import java.time.LocalDate;
import java.time.YearMonth;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;


    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

/*        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        this.crearVistaListaCalendario(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void crearVistaListaCalendario(View root) {

        YearMonth yearMonth = YearMonth.of( 2021, 5 );
        LocalDate last = yearMonth.atEndOfMonth();
        int currentday = LocalDate.now().getDayOfMonth();


        Integer[] dates = new Integer[last.getDayOfMonth()];
        int[] sentiment_color = new int[last.getDayOfMonth()];

        int colors[] = {
                R.color.light,
                R.color.red,
                R.color.orange,
                R.color.yellow,
                R.color.green,
        };

        for (int i = 0; i < last.getDayOfMonth(); i++) {
            dates[i] = i+1;
            sentiment_color[i] = colors[i % 5];
        }

        ListView listView = (ListView) root.findViewById(R.id.calendar_dates_list);

        // For populating list data
        CalendarListAdapter calendarList = new CalendarListAdapter(getActivity(), dates, sentiment_color, currentday);
        listView.setAdapter(calendarList);
    }
}
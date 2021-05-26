package com.proyectosm.mentalhealthapp.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.proyectosm.mentalhealthapp.R;
import com.proyectosm.mentalhealthapp.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    private ChatModel chatrecord[] = {
            new ChatModel("Question 1", true),
            new ChatModel("Answer 1: aksldjf laksjdf lasjdfl kjadhsf lkjsdhfla kjdshf lakjsd hfalksjd fh", false),
            new ChatModel("Question 2", true),
            new ChatModel("Answer 2: asdkfh alksjdhf lkjasdhf lakjsdhf lakjsd hflaskjd hflaksjdfhlakjdshfa", false),
            new ChatModel("Question 3", true),
            new ChatModel("Answer 3: asdfkalsjkd hfalkjsd hkjas dhfkajsdf lkahsdfl kajshf", false),
            new ChatModel("Question 4", true),
            new ChatModel("Answer 4: asdflkjasldkfhalksjd ihfakjhf jklashfl kjadhfl akjhdfljk ahsdfl kjahdlfk jhasldkjf haljdkhf", false),
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        ListView listView = (ListView) root.findViewById(R.id.chat_container);

        // Construct the data source
        ArrayList<ChatModel> arrayOfChatEntries = new ArrayList<ChatModel>();

        // For populating list data
        ChatListAdapter chatsListAdapter = new ChatListAdapter(getActivity(), arrayOfChatEntries);
        listView.setAdapter(chatsListAdapter);

        chatsListAdapter.addAll(chatrecord);



//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.proyectosm.mentalhealthapp.ui.dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.melnykov.fab.FloatingActionButton;
import com.proyectosm.mentalhealthapp.R;
import com.proyectosm.mentalhealthapp.Service.RecordingService;
import com.proyectosm.mentalhealthapp.databinding.FragmentDashboardBinding;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardFragment extends Fragment {

    public DatabaseReference mDatabaseReference;
    public FirebaseDatabase mFirebase;

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    @BindView(R.id.botonGrabar) FloatingActionButton botonG;
    @BindView(R.id.botonParar) Button botonP;
    //@BindView(R.id.estadoGrabacion) TextView estadoGrabacion;

    private boolean mStartRecording = true;
    private boolean mPauseRecording = true;
    long timeWhenPaused = 0;
    private boolean success;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mFirebase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebase.getReference();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View recordView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, recordView);

        /*dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //View view = inflater.inflate(R.layout.fragment_dashboard, container, false);*/


        return recordView;
        /*final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("hey");
            }
        });
        return root;*/
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        botonP.setVisibility(View.GONE);
    }
    
    @OnClick(R.id.botonGrabar)
    public void recordAudio()
    {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.RECORD_AUDIO },
                    10);
        } else {
            mDatabaseReference.child("prueba").setValue(1);
            onRecord(mStartRecording);
            mStartRecording = !mStartRecording;
        }
    }

    private void onRecord(boolean start)
    {
        Intent intent = new Intent(getActivity(), RecordingService.class);

        if(start)
        {
            botonG.setImageResource(R.drawable.ic_white_stop);
            Toast.makeText(getContext(), "Grabando", Toast.LENGTH_SHORT).show();

            String fileName = getContext().getExternalFilesDir(null) + "/MySoundRec/";
            File folder = new File (fileName);
            if(!folder.exists())
            {
                success = folder.mkdir();
            }

            if (success) {
                Toast.makeText(getActivity(), fileName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Fichero no creado", Toast.LENGTH_SHORT).show();
            }
            
            getActivity().startService(intent);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else 
        {
            botonG.setImageResource(R.drawable.ic_white_mic);
            timeWhenPaused = 0;
            //estadoGrabacion.setText("Pulsa para empezar a grabar");
            
            getActivity().stopService(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recordAudio();
            }else{
                //User denied Permission.
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
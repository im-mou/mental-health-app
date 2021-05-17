package com.proyectosm.mentalhealthapp.ui.dashboard;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.proyectosm.mentalhealthapp.R;
import com.proyectosm.mentalhealthapp.databinding.FragmentDashboardBinding;

import java.io.File;
import java.io.IOException;

public class DashboardFragment extends Fragment implements MediaPlayer.OnCompletionListener {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    Button botonGrabar, botonParar;
    TextView estadoGrabacion;
    MediaRecorder recorder;
    MediaPlayer player;
    File archivo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        botonGrabar = (Button) container.findViewById(R.id.botonGrabar);
        botonParar = (Button) container.findViewById(R.id.botonParar);


        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    public void grabar(View v) {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File path = new File(Environment.getExternalStorageDirectory()
                .getPath());
        try {
            archivo = File.createTempFile("temporal", ".3gp", path);
        } catch (IOException e) {
        }
        recorder.setOutputFile(archivo.getAbsolutePath());
        try {
            recorder.prepare();
        } catch (IOException e) {
        }
        recorder.start();
        estadoGrabacion.setText("Grabando");
        botonGrabar.setEnabled(false);
        botonParar.setEnabled(true);
    }

    public void detener(View v) {
        recorder.stop();
        recorder.release();
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        try {
            player.setDataSource(archivo.getAbsolutePath());
        } catch (IOException e) {
        }
        try {
            player.prepare();
        } catch (IOException e) {
        }
        botonGrabar.setEnabled(true);
        botonParar.setEnabled(false);
        //b3.setEnabled(true);
        estadoGrabacion.setText("Listo para reproducir");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
package com.proyectosm.mentalhealthapp.ui.dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.proyectosm.mentalhealthapp.R;
import com.proyectosm.mentalhealthapp.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private Button btnRespuesta;
    private TextView stateGrabacion;

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    private ChatModel chatrecord[] = {
            new ChatModel("Question 1", true),
            new ChatModel("Answer 1: Lorem Ipsum is simply dummy text of the printing and typesetting industry.", false),
            new ChatModel("Question 2", true),
            new ChatModel("Answer 2: orem Ipsum has been the industry's standard dummy text ever since the 1500s", false),
            new ChatModel("Question 3", true),
            new ChatModel("Answer 3: when an unknown printer took a galley of type and scrambled it to make a type specimen book", false),
            new ChatModel("Question 4", true),
            new ChatModel("Answer 4: It has survived not only five centuries, but also the leap into electronic typesetting", false),
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

        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }

        // Variables principales del layout
        stateGrabacion = getView().findViewById(R.id.estadoGrabacion);
        btnRespuesta = getView().findViewById(R.id.botonRespuesta);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());

        // Se realiza un Intent para llamar al Speech Recognizer
        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Se le indica como info extra en el intent que se habla de forma natural y que el idioma
        // de introducción será el local (en este caso español)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        // Según cómo reaccione el bot, se realizan diferentes cosas
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            // Cuando empieza a detectar texto borra y escribe una frase para informar al usuario
            @Override
            public void onBeginningOfSpeech() {
                stateGrabacion.setText("");
                stateGrabacion.setHint("Grabando tu voz...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                stateGrabacion.setText("Esperando Voz");
            }

            @Override
            public void onError(int i) {

            }

            // Cuando el Speech Recognition termina de detectar palabras las escribe en la barra
            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                stateGrabacion.setText(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        // La función se lanza cuando se pone el dedo sobre el botón de grabar
        btnRespuesta.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                // Al soltar el dedo finaliza
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    speechRecognizer.stopListening();
                }

                // Al pulsar el boton inicia
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    speechRecognizer.startListening(speechRecognizerIntent);
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    // Pregunta si hay permisos de grabación
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }

    // Si no tiene permisos, los pide
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){

            // Operacion exitosa
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(getContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
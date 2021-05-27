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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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
    // Variables necesarias para la captación de audio
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private ImageButton btnMicro;
    private ImageButton btnEnviar;
    private EditText stateGrabacion;

    // Variables para la muestra del chat por pantalla
    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    // Chat de ejemplo
    private ChatModel chatrecord[] = {
            // Se crean diversas instancias de ChatModel y luego se cargan dentro de chatsListAdapter
            new ChatModel("¿Cómo te has sentido al despertarte?", true),
            new ChatModel("Lorem Ipsum is simply dummy text of the printing and typesetting industry.", false),
            new ChatModel("¿Avanzaste mucho en tus proyectos ayer? ¿Por qué?", true),
            new ChatModel("When an unknown printer took a galley of type and scrambled it to make a type specimen book", false),
            new ChatModel("¿Cómo crees que te irá el día hoy?", true),
            new ChatModel("Lorem Ipsum has been the industry's standard dummy text ever since the 1500s", false),
            new ChatModel("¿Te sientes con seguridad en tu sitio de trabajo? ¿Y por qué?", true),
            new ChatModel("It has survived not only five centuries, but also the leap into electronic typesetting", false),
            new ChatModel("¿Has estado relajado/a hoy?", true),
            new ChatModel("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.", false),
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = (ListView) root.findViewById(R.id.chat_container);

        // Construye la fuente de los datos
        ArrayList<ChatModel> arrayOfChatEntries = new ArrayList<ChatModel>();

        // Recibe los chatModels y carga los datos
        ChatListAdapter chatsListAdapter = new ChatListAdapter(getActivity(), arrayOfChatEntries);
        listView.setAdapter(chatsListAdapter);

        chatsListAdapter.addAll(chatrecord);

        return root;
    }


    @Override
    public void onStart() {
        super.onStart();

        //Cuando comienza, chequea si se tienen permisos para grabar y si no es el caso los pide
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }

        // Variables principales del layout
        stateGrabacion = getView().findViewById(R.id.estadoGrabacion);
        btnMicro = getView().findViewById(R.id.botonMicro);
        btnMicro.setImageResource(R.drawable.ic_mic);
        btnEnviar = getView().findViewById(R.id.botonEnviar);
        btnEnviar.setImageResource(R.drawable.ic_send);
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
        btnMicro.setOnTouchListener(new View.OnTouchListener() {
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

    // Pide permisos de grabación
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }

    // Recoge los resultados de la petición y lo muestra por pantalla si se han dado
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){

            // Operacion exitosa
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(getContext(),"Permiso concedido",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
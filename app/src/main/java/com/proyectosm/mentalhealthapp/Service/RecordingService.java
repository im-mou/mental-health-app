package com.proyectosm.mentalhealthapp.Service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.proyectosm.mentalhealthapp.ui.dashboard.RecordingItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RecordingService extends Service {
    private static final String LOG_TAG = "AudioRecordTest";
    MediaRecorder mediaRecorder;
    boolean iniciado = false;
    long mStartingTimeMillis = 0;
    long mElapsedMillis = 0;

    File file;

    String fileName;

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        startRecording();
        return START_STICKY;
    }

    private void startRecording() {
        long tsLong = System.currentTimeMillis()/1000;
        String ts = Long.toString(tsLong);

        Toast.makeText(getApplicationContext(), "grabandooooo", Toast.LENGTH_SHORT).show();
        fileName = "audio_"+ts;

        file = new File(getExternalFilesDir( null ) + "/MySoundRec/"+fileName+".mp3");
        if (!file.exists()) {
            AssetManager assetManager = getAssets();
            try {
                file.createNewFile();
                InputStream in = assetManager.open(fileName+".mp3");
                OutputStream out = new FileOutputStream(getExternalFilesDir( null ) + "/MySoundRec/"+fileName+".mp3");
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1)
                    out.write(buffer, 0, read);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (IOException e) {
                Log.e("tag", "Failed to copy asset file: ", e);
            }
        }

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(file.getAbsolutePath());
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setAudioChannels(1);

        try{
            mediaRecorder.prepare();
            iniciado = !iniciado;

        }catch (IllegalStateException | IOException e) {
            Log.e(LOG_TAG, "prepared() failed");
            e.printStackTrace();
        }
        if(iniciado)
        {
            mediaRecorder.start();
            mStartingTimeMillis = System.currentTimeMillis();
        }
    }

    private void stopRecording()
    {
        if(iniciado)
        {
            mediaRecorder.stop();
            mElapsedMillis = (System.currentTimeMillis() - mStartingTimeMillis);
            mediaRecorder.release();
            Toast.makeText(getApplicationContext(), "Grabacion guardada en "+file.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Ha habido un error al guardar",
                    Toast.LENGTH_SHORT).show();
        }

        //RecordingItem recordItem = new RecordingItem(fileName, file.getAbsolutePath(),mElapsedMillis,System.currentTimeMillis());
    }

    @Override
    public void onDestroy(){
        if(mediaRecorder != null)
        {
            stopRecording();
        }

        super.onDestroy();
    }
}

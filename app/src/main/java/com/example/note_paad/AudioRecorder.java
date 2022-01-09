package com.example.note_paad;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;


public class AudioRecorder {

    final MediaRecorder recorder = new MediaRecorder();
    public final String path;
    int duration ;
    MediaPlayer mp;
    public AudioRecorder(String path) {
        this.path = sanitizePath(path);
    }

    private String sanitizePath(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (!path.contains(".")) {
            path += ".3gp";
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + path;
    }

    public String start() throws IOException {
        String state = android.os.Environment.getExternalStorageState();
        if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            throw new IOException("SD Card is not mounted.  It is " + state
                    + ".");
        }

        // make sure the directory we plan to store the recording in exists
        File directory = new File(path).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Path to file could not be created.");
        }

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(path);
        recorder.prepare();
        recorder.start();
        return path;
    }

    public void stop() throws IOException {
        recorder.stop();
        recorder.release();
    }

    public void playarcoding(String path) throws IOException {
        mp = new MediaPlayer();
        FileInputStream fis = null;
            File directory = new File(path);
            if (directory.exists()){
                Log.i("onCsss", "playarcoding: yess ");
            }
            fis = new FileInputStream(directory);
            mp.setDataSource(fis.getFD());
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mp.setOnPreparedListener(mediaPlayer -> mediaPlayer.start());
            mp.prepareAsync();
        //mp.release();


//        String time = String.format("%d min, %d sec",
//                TimeUnit.MILLISECONDS.toMinutes(duration),
//                TimeUnit.MILLISECONDS.toSeconds(duration) -
//                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        //Log.i("playarcoding", during(path)+"");


        //mp.setVolume(10, 10);
    }
    public int during (String path)  {
        File yourFile =new File(path);;
        MediaPlayer mp = new MediaPlayer();
        FileInputStream fs;
        FileDescriptor fd;
        try {
            fs = new FileInputStream(yourFile);
            fd = fs.getFD();
            mp.setDataSource(fd);
            mp.prepare();
            int length = mp.getDuration();
            mp.release();
            return length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    public void stoparcoding() {
        mp.stop();
        //mp.setVolume(10, 10);
    }

}
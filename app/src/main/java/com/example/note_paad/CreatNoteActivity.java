package com.example.note_paad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreatNoteActivity extends AppCompatActivity {
    private EditText title,subtite,notetext;
    private TextView time;
    private ImageView mic_open;
    private ImageView mic_play;
    private ImageView mic_stop;
    private ImageView mic_close;
    RelativeLayout mic_control;
    AudioRecorder recorder;
    String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
    Boolean flag =false;
    NoteDataAccess access = new NoteDataAccess(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_note);
        ImageView ImageView = findViewById(R.id.imageBack);
        ImageView.setOnClickListener(view -> onBackPressed());
        title = findViewById(R.id.inputNoteTitle);
        mic_open = findViewById(R.id.mic);
        mic_control = findViewById(R.id.mic_control);
        mic_play = findViewById(R.id.record_start);
        mic_close = findViewById(R.id.close);
        mic_stop = findViewById(R.id.record_stop);
        subtite = findViewById(R.id.inputNoteSubtitle);
        notetext = findViewById(R.id.inputNote);
        time = findViewById(R.id.textDateTime);
        mic_control.setVisibility(View.GONE);
        mic_stop.setVisibility(View.GONE);

        ActivityCompat.requestPermissions(CreatNoteActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        String time_str = new SimpleDateFormat("EEEE,  dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date());
        time.setText(time_str);
        mFileName+="/"+"notepad-"+time_str+".3gp";

        ImageView save = findViewById(R.id.imageSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (save()){
                    onBackPressed();
                }

            }
        });
        mic_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(CreatNoteActivity.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        2);
                mic_control.setVisibility(View.VISIBLE);
            }
        });
        mic_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mic_stop.setVisibility(View.VISIBLE);
                mic_play.setVisibility(View.GONE);


                recorder =new AudioRecorder(mFileName);
                try {


                    recorder.start();
                    flag=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mic_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mic_stop.setVisibility(View.GONE);
                mic_play.setVisibility(View.VISIBLE);
                mic_control.setVisibility(View.GONE);
                try {
                    recorder.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    mic_close.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mic_stop.setVisibility(View.GONE);
            mic_play.setVisibility(View.VISIBLE);
            mic_control.setVisibility(View.GONE);
            try {
                if (recorder!=null)
                    recorder.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });}


    private boolean save(){
        if (title.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "please fill the title", Toast.LENGTH_SHORT).show();
            return false;
        }else if (subtite.getText().toString().trim().isEmpty()&&notetext.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "note cant be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        access.openDB();
        if (flag)
            access.addNewNote(new note_modle(mFileName,notetext.getText().toString(),time.getText().toString(),title.getText().toString(),subtite.getText().toString(),null));
        else
            access.addNewNote(new note_modle(null,notetext.getText().toString(),time.getText().toString(),title.getText().toString(),subtite.getText().toString(),null));
        access.closeDB();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CreatNoteActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(CreatNoteActivity.this, "Permission denied to write or record your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(CreatNoteActivity.this, "Permission denied to record your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
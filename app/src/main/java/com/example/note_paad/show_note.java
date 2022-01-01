package com.example.note_paad;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class show_note  extends AppCompatActivity {
    private EditText title,subtite,notetext;
    private TextView time;
    private ImageView mic_open;
    private ImageView mic_play;
    private ImageView mic_stop;
    private ImageView mic_close;
    RelativeLayout mic_control;
    AudioRecorder recorder;
    //String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
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
    }
}

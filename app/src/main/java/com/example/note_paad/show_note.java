package com.example.note_paad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
    private ImageView image;
    private ImageView draw;
    View view ;
    private ImageView mic_close;
    RelativeLayout mic_control;
    AudioRecorder recorder;
    //String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
    Boolean flag =false;
    note_modle note;
    NoteDataAccess access = new NoteDataAccess(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_note);
        ImageView ImageView = findViewById(R.id.imageBack);
        ImageView.setOnClickListener(view -> onBackPressed());
        title = findViewById(R.id.inputNoteTitle);
        title.setEnabled(false);
        mic_open = findViewById(R.id.mic);
        view = findViewById(R.id.view);
        image = findViewById(R.id.image);
        draw = findViewById(R.id.drawi);
        mic_control = findViewById(R.id.mic_control);
        mic_play = findViewById(R.id.record_start);
        mic_close = findViewById(R.id.close);
        mic_stop = findViewById(R.id.record_stop);
        subtite = findViewById(R.id.inputNoteSubtitle);
        subtite.setEnabled(false);
        notetext = findViewById(R.id.inputNote);
        notetext.setEnabled(false);
        time = findViewById(R.id.textDateTime);
        mic_control.setVisibility(View.GONE);
        mic_stop.setVisibility(View.GONE);
        Bundle extraas = getIntent().getExtras();
        try {
            if (extraas == null) {
                Log.i("TAgg", "null");
                onBackPressed();
            } else {
                note = MainActivity.notes.get(extraas.getInt("index"));
                extraas.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (note==null){
            onBackPressed();
        }
        time.setText(note.getTime());
        subtite.setText(note.getSubtitle());
        title.setText(note.getTitle());
        notetext.setText(note.getText());
        view.setVisibility(View.GONE);
        if (note.getImage()!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(note.getImage(), 0, note.getImage().length);
            image.setImageBitmap(bitmap);

        }
        if (note.getDraw()!=null){
            draw.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(note.getDraw(), 0, note.getDraw().length);
            draw.setImageBitmap(bitmap);
        }
        if (note.getDraw()!=null&&note.getImage()!=null)
            view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(show_note.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}

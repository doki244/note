package com.example.note_paad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreatNoteActivity extends AppCompatActivity {
    private EditText title,subtite,notetext;
    private TextView time;
    NoteDataAccess access = new NoteDataAccess(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_note);
        ImageView ImageView = findViewById(R.id.imageBack);
        ImageView.setOnClickListener(view -> onBackPressed());
        title = findViewById(R.id.inputNoteTitle);
        subtite = findViewById(R.id.inputNoteSubtitle);
        notetext = findViewById(R.id.inputNote);
        time = findViewById(R.id.textDateTime);
        time.setText(new SimpleDateFormat("EEEE,  dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date()));
        ImageView save = findViewById(R.id.imageSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (save()){
                    onBackPressed();
                }

            }
        });
    }

    private boolean save(){
        if (title.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "please fill the title", Toast.LENGTH_SHORT).show();
            return false;
        }else if (subtite.getText().toString().trim().isEmpty()&&notetext.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "note cant be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        access.openDB();
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
}
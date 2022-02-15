package com.example.note_paad;

import static com.example.note_paad.CreatNoteActivity.notemodle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import uk.co.senab.photoview.PhotoViewAttacher;

public class show_note  extends AppCompatActivity {
    private EditText title,subtite,notetext;
    private TextView time;
    private ImageView mic_open;
    private ImageView mic_play;
    private ImageView mic_stop;
    private ImageView image;
    private ImageView draw;
    private ImageView drawing;
    private ImageView add_image;
    private TextView min_view;
    private TextView sec_view;
    private ImageView remove_img;
    private ImageView remove_draw;
    LinearLayout linearLayout ;
    public static Bitmap img;
    //View view ;
    private ImageView mic_close;
    RelativeLayout mic_control;
    AudioRecorder recorder;
    String mFileName = "";
    //String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
    Boolean flag =false;
    note_modle note;
    int dur;
    NoteDataAccess access = new NoteDataAccess(this);
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_note);
        ImageView ImageView = findViewById(R.id.imageBack);
        ImageView.setOnClickListener(view -> onBackPressed());
        title = findViewById(R.id.inputNoteTitle);
        title.setEnabled(false);
        mic_open = findViewById(R.id.mic);
        //view = findViewById(R.id.view);
        ImageView edit = findViewById(R.id.imageSave);
        edit.setTag("edit");
        image = findViewById(R.id.image);
        draw = findViewById(R.id.drawi);
        drawing = findViewById(R.id.draw);
        drawing.setVisibility(View.GONE);
        add_image = findViewById(R.id.add_image);
        add_image.setVisibility(View.GONE);
        remove_img = findViewById(R.id.remove_img);
        remove_draw = findViewById(R.id.remove_draw);
        remove_img.setVisibility(View.GONE);
        remove_draw.setVisibility(View.GONE);
        //save.setVisibility(View.GONE);
        edit.setImageResource(R.drawable.edit);
        drawing.setVisibility(View.GONE);
        mic_control = findViewById(R.id.mic_control);
        linearLayout = findViewById(R.id.LinearLayout);
        linearLayout.setVisibility(View.GONE);
        draw.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
        mic_play = findViewById(R.id.record_start);
        mic_close = findViewById(R.id.close);
        min_view = findViewById(R.id.min);
        sec_view = findViewById(R.id.second);
        mic_stop = findViewById(R.id.record_stop);
        subtite = findViewById(R.id.inputNoteSubtitle);
        subtite.setEnabled(false);
        notetext = findViewById(R.id.inputNote);
        notetext.setEnabled(false);
        time = findViewById(R.id.textDateTime);
        mic_control.setVisibility(View.GONE);
        mic_stop.setVisibility(View.GONE);

        mic_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mic_control.setVisibility(View.VISIBLE);
            }
        });

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
        mic_play.setImageResource(R.drawable.ic_play);

        edit.setOnClickListener(view -> {
            Intent intent = new Intent(show_note.this,CreatNoteActivity.class);
            startActivity(intent);
            notemodle = note;
            if (edit.getTag().equals("edit")){
//                notetext.setEnabled(true);
//                subtite.setEnabled(true);
//                title.setEnabled(true);
//                edit.setTag("");
            }else
                //save
                Toast.makeText(this, "by", Toast.LENGTH_SHORT).show();

        });
        mFileName = note.getVoice_path();

        if (mFileName==null){
            mic_open.setVisibility(View.GONE);
        }else {
            recorder =new AudioRecorder(mFileName);
            dur = recorder.during(mFileName);
            int min = (int) TimeUnit.MILLISECONDS.toMinutes(dur);
            int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(dur) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(dur)));
            Log.i("dorrr", min+"  "+sec);
            min_view.setText(min+"");
            sec_view.setText(sec+"");
        }
        //Log.i("onCsss ", mFileName);
        mic_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mic_stop.setVisibility(View.VISIBLE);
                mic_play.setVisibility(View.GONE);

                try {

                    recorder.playarcoding(mFileName);
                    new CountDownTimer(dur, 1000) {

                        public void onTick(long millisUntilFinished) {
                            int min = (int) TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                            int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                            Log.i("dorrr", min+"  "+sec);
                            min_view.setText(min+"");
                            sec_view.setText(sec+"");

                        }

                        public void onFinish() {
                            mic_stop.setVisibility(View.GONE);
                            mic_play.setVisibility(View.VISIBLE);
                            int min = (int) TimeUnit.MILLISECONDS.toMinutes(dur);
                            int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(dur) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(dur)));
                            //Log.i("dorrr", min+"  "+sec);
                            min_view.setText(min+"");
                            sec_view.setText(sec+"");

                        }

                    }.start();
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
                recorder.stoparcoding();
                recorder=null;

            }
        });
        mic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mic_stop.setVisibility(View.GONE);
                mic_play.setVisibility(View.VISIBLE);
                mic_control.setVisibility(View.GONE);
                if (recorder!=null)
                    recorder.stoparcoding();
            }
        });



        time.setText(note.getTime());
        subtite.setText(note.getSubtitle());
        title.setText(note.getTitle());
        notetext.setText(note.getText());
        //view.setVisibility(View.GONE);
        if (note.getImage()!=null){
            linearLayout.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(note.getImage(), 0, note.getImage().length);
            image.setImageBitmap(bitmap);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(show_note.this,imageview.class);
                    img = bitmap;
                    startActivity(intent);
                }
            });
        }
        if (note.getDraw()!=null){
            linearLayout.setVisibility(View.VISIBLE);
            draw.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(note.getDraw(), 0, note.getDraw().length);
            draw.setImageBitmap(bitmap);
            PhotoViewAttacher pAttacher;
            pAttacher = new PhotoViewAttacher(draw);
            pAttacher.update();


        }
        if (note.getDraw()!=null&&note.getImage()!=null){
            //view.setVisibility(View.VISIBLE);
        }
        CoordinatorLayout CoordinatorLayout = findViewById(R.id.coordinator);

        if (MainActivity.curent_theme==MainActivity.light_theme){
            //linearLayout.setBackgroundColor(dark.getItem());
            CoordinatorLayout.setBackgroundColor(MainActivity.light.getCreat_background());
            title.setTextColor(MainActivity.light.text_color);
            notetext.setTextColor(MainActivity.light.text_color);
            subtite.setTextColor(MainActivity.light.text_color);

        }else if (MainActivity.curent_theme==MainActivity.dark_theme){//go to light
            CoordinatorLayout.setBackgroundColor(MainActivity.dark.getCreat_background());
            title.setTextColor(MainActivity.dark.text_color);
            notetext.setTextColor(MainActivity.dark.text_color);
            subtite.setTextColor(MainActivity.dark.text_color);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(show_note.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}

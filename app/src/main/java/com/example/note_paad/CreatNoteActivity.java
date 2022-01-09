package com.example.note_paad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.afollestad.materialdialogs.MaterialDialog;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class CreatNoteActivity extends AppCompatActivity {
    private EditText title,subtite,notetext;
    private TextView time;
    private ImageView mic_open;
    private ImageView mic_play;
    private ImageView mic_stop;
    private ImageView mic_close;
    private ImageView image;
    private ImageView drawi;
    private ImageView add_img;
    private TextView min_view;
    private TextView sec_view;
    long startTime, timeInMilliseconds = 0;
    Handler customHandler = new Handler();
    //View view ;
    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE_PHOTO = 2;
    private Bitmap thumbnail;
    private Handler progressBarbHandler = new Handler();
    private ImageView drawing;
    private LinearLayout LinearLayout;
    private int progressBarStatus;
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
        drawing = findViewById(R.id.draw);
        mic_play = findViewById(R.id.record_start);
        min_view = findViewById(R.id.min);
        sec_view = findViewById(R.id.second);
        mic_close = findViewById(R.id.close);
        LinearLayout = findViewById(R.id.LinearLayout);
        drawi = findViewById(R.id.drawi);
       // view = findViewById(R.id.view);
        //view.setVisibility(View.GONE);
        drawi.setVisibility(View.GONE);
        LinearLayout.setVisibility(View.GONE);
        mic_stop = findViewById(R.id.record_stop);
        image = findViewById(R.id.image);
        add_img = findViewById(R.id.add_image);
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

                    mFileName=null;
                    mFileName = recorder.start();
                    start();
                    Log.i("mFileName", mFileName);
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
                    stop();
                    recorder=null;
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
    });
        drawing.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(),draw.class);
            startActivity(intent);
            setProgressBar();
        }
    });
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("set your image")
                        .setPositiveButton("gallery", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("camera", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, CAPTURE_PHOTO);
                            }
                        })
                        .setIcon(R.drawable.addimage)
                        .show();
                image.setDrawingCacheEnabled(true);
                image.buildDrawingCache();



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
        byte[] imagee =null;
        try {
            Bitmap bitmap = image.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            imagee = baos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }

        access.openDB();
        if (flag)
            access.addNewNote(new note_modle(mFileName,notetext.getText().toString(),time.getText().toString(),title.getText().toString(),subtite.getText().toString(),imagee,draw.draw));
        else
            access.addNewNote(new note_modle(null,notetext.getText().toString(),time.getText().toString(),title.getText().toString(),subtite.getText().toString(),imagee,draw.draw));
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

        }
    }
    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");

        //set Progress Bar
        setProgressBar();
        //set profile picture form camera
        //image.setMaxWidth(100);
        image.setImageBitmap(thumbnail);
        LinearLayout.setVisibility(View.VISIBLE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_PHOTO){
            if(resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    setProgressBar();
                    //set profile picture form gallery
                    image.setImageBitmap(selectedImage);
                    LinearLayout.setVisibility(View.VISIBLE);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }else if(requestCode == CAPTURE_PHOTO){
            if(resultCode == RESULT_OK) {
                onCaptureImageResult(data);
            }
        }
    }
    public void setProgressBar(){
        ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
         progressBarStatus = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressBarStatus < 100){
                    progressBarStatus += 30;

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progressBarbHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                if (progressBarStatus >= 100) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.dismiss();
                }

            }
        }).start();
    }
    public static String getDateFromMillis(long d) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }

    public void start() {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
    }

    public void stop() {
        customHandler.removeCallbacks(updateTimerThread);
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            int min = (int) TimeUnit.MILLISECONDS.toMinutes(timeInMilliseconds);
            int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(timeInMilliseconds) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMilliseconds)));
            //Log.i("dorrr", min+"  "+sec);
            min_view.setText(min+"");
            sec_view.setText(sec+"");
            //min_view.setText(getDateFromMillis(timeInMilliseconds));
            customHandler.postDelayed(this, 1000);
        }
    };
}
//abstract class CountUpTimer extends CountDownTimer {
//    private static final long INTERVAL_MS = 1000;
//    private final long duration;
//
//    protected CountUpTimer(long durationMs) {
//        super(durationMs, INTERVAL_MS);
//        this.duration = durationMs;
//    }
//
//    public abstract void onTick(int second);
//
//    @Override
//    public void onTick(long msUntilFinished) {
//        int second = (int) ((duration - msUntilFinished) / 1000);
//        onTick(second);
//    }
//
//    @Override
//    public void onFinish() {
//        onTick(duration / 1000);
//    }
//}

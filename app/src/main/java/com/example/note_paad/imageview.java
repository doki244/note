package com.example.note_paad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.jsibbold.zoomage.ZoomageView;

public class imageview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        ZoomageView imageView = findViewById(R.id.myZoomageView);
        imageView.setImageBitmap(show_note.img);

    }
}
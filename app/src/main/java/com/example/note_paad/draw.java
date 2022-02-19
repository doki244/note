package com.example.note_paad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.byox.drawview.enums.BackgroundScale;
import com.byox.drawview.enums.BackgroundType;
import com.byox.drawview.enums.DrawingCapture;
import com.byox.drawview.views.DrawView;

public class draw extends AppCompatActivity {
    private DrawView mDrawView;
    private ImageView back;
    private ImageView done;
    private ImageView red;
    private ImageView undo;
    private ImageView redo;
    private ImageView blue;
    private ImageView black;
    private ImageView purple;
    private ImageView yellow;
    public static byte[] draw = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);
        Log.i("7777777", "onCreate: ");
        mDrawView = (DrawView) findViewById(R.id.draw_view);
        back =  findViewById(R.id.imageBack);
        done =  findViewById(R.id.done);
        red = findViewById(R.id.red);
        redo = findViewById(R.id.redo);
        undo = findViewById(R.id.undo);
        black = findViewById(R.id.black);
        blue = findViewById(R.id.blue);
        yellow = findViewById(R.id.yellow);
        purple = findViewById(R.id.purple);
        mDrawView.setDrawColor(Color.BLACK);

//        Bitmap background = ((BitmapDrawable)getResources().getDrawable(R.drawable.white)).getBitmap();
//        mDrawView.setBackgroundImage(background, BackgroundType.BITMAP, BackgroundScale.CENTER_CROP);
//        mDrawView.setDrawWidth(20);


//        mDrawView.setOnDrawViewListener(new DrawView.OnDrawViewListener() {
//            @Override
//            public void onStartDrawing() {
//                // Your stuff here
//            }
//
//            @Override
//            public void onEndDrawing() {
//
//
//            }
//
//            @Override
//            public void onClearDrawing() {
//                // Your stuff here
//            }
//
//            @Override
//            public void onRequestText() {
//                // Your stuff here
//            }
//
//            @Override
//            public void onAllMovesPainted() {
//                // Your stuff here
//            }
//        });
        done.setOnClickListener(view -> {
            Object[] createCaptureResponse = mDrawView.createCapture(DrawingCapture.BYTES);
            draw = (byte[]) createCaptureResponse[0];
            onBackPressed();
        });
        back.setOnClickListener(view -> onBackPressed());
        black.setOnClickListener(view -> mDrawView.setDrawColor(Color.BLACK));
        purple.setOnClickListener(view -> mDrawView.setDrawColor(Color.GREEN));
        red.setOnClickListener(view -> mDrawView.setDrawColor(Color.RED));
        blue.setOnClickListener(view -> mDrawView.setDrawColor(Color.BLUE));
        yellow.setOnClickListener(view -> mDrawView.setDrawColor(Color.YELLOW));
        undo.setOnClickListener(view -> {
            if (mDrawView.canUndo())
                mDrawView.undo();
        });
        redo.setOnClickListener(view -> {
            if (mDrawView.canRedo())
                mDrawView.redo();
       });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }


}
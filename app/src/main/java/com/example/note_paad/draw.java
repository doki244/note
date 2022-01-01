package com.example.note_paad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.byox.drawview.enums.DrawingCapture;
import com.byox.drawview.views.DrawView;

public class draw extends AppCompatActivity {
    private DrawView mDrawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        mDrawView = (DrawView) findViewById(R.id.draw_view);
        //mDrawView.setDrawColor(R.color.colorPrimery);
        mDrawView.setDrawWidth(20);
        mDrawView.setOnDrawViewListener(new DrawView.OnDrawViewListener() {
            @Override
            public void onStartDrawing() {
                // Your stuff here
            }

            @Override
            public void onEndDrawing() {
                mDrawView.setDrawColor(R.color.colorPrimery);

            }

            @Override
            public void onClearDrawing() {
                // Your stuff here
            }

            @Override
            public void onRequestText() {
                // Your stuff here
            }

            @Override
            public void onAllMovesPainted() {
                // Your stuff here
            }
        });
        Object[] createCaptureResponse = mDrawView.createCapture(DrawingCapture.BITMAP);
        //DrawingCapture.BYTES
        Bitmap v =(Bitmap) createCaptureResponse[0];
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
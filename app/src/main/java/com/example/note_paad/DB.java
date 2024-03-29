package com.example.note_paad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Blob;

public class DB extends SQLiteOpenHelper {
    public static final String DB_NAME = "note.db";
    public static final int DB_VERSION =7;

    public static final String TABLE_NOTE = "notes";
    public static final String ID = "id";
    public static final String DRAW = "draw";
    public static final String TITLE = "title";
    public static final String IMG = "img";
    public static final String SUBTITLE = "subtitle";
    public static final String TEXT = "text";
    public static final String TIME = "time";
    public static final String VOICE_PATH = "voic_path";

    public DB (@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query  = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTE + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                TITLE + " TEXT , " +
                DRAW + " BLOB , " +
                IMG + " BLOB , " +
                SUBTITLE + " TEXT , " +
                TIME + " TEXT NOT NULL , " +
                TEXT + " TEXT , " +
                VOICE_PATH + " TEXT )";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NOTE ;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
}

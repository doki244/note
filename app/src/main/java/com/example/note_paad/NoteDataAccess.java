package com.example.note_paad;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteDataAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    public NoteDataAccess(@Nullable Context context) {
        openHelper = new DB(context);
    }
    public void openDB(){
        database = openHelper.getWritableDatabase();
    }
    public void closeDB(){
        database.close();
    }
    public boolean addNewNote(note_modle model){
        try {
            String SUBTITLE= model.getSubtitle();
            String TITLE= model.getTitle();
            String TIME=model.getTime();
            String  TEXT=model.getText();
            byte[] image =model.getImage();
            byte[] draw =model.getDraw();
            String VOICE_PATH = model.getVoice_path();
            ContentValues cv = new ContentValues();
            cv.put(DB.TITLE,TITLE);
            cv.put(DB.TEXT,TEXT);
            cv.put(DB.SUBTITLE,SUBTITLE);
            cv.put(DB.TIME,TIME);
            cv.put(DB.DRAW,draw);
            cv.put(DB.IMG,image);
            cv.put(DB.VOICE_PATH,VOICE_PATH);
            database.insert(DB.TABLE_NOTE,null,cv);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    @SuppressLint("Range")
    public note_modle getnote(String id) {
        String TITLE;
        String SUBTITLE;
        int idd;
        String TIME;
        byte[] image;
        byte[] draw;
        String  TEXT;
        String VOICE_PATH;
        String query = "SELECT * FROM " + DB.TABLE_NOTE + " where id ='" + id + "'";
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            idd = cursor.getInt(cursor.getColumnIndex(DB.ID));
            TIME = cursor.getString(cursor.getColumnIndex(DB.TIME));
            TEXT = cursor.getString(cursor.getColumnIndex(DB.TEXT));
            VOICE_PATH = cursor.getString(cursor.getColumnIndex(DB.VOICE_PATH));
            SUBTITLE = cursor.getString(cursor.getColumnIndex(DB.SUBTITLE));
            draw = cursor.getBlob(cursor.getColumnIndex(DB.DRAW));
            image = cursor.getBlob(cursor.getColumnIndex(DB.IMG));
            TITLE = cursor.getString(cursor.getColumnIndex(DB.TITLE));
            return new note_modle(VOICE_PATH,TEXT,TIME,TITLE,SUBTITLE,image,idd,draw);
        }
        else{
            return null;
        }
    }
    @SuppressLint("Range")
    public ArrayList<note_modle> getall() {
        String TITLE;
        String SUBTITLE;
        int idd;
        String TIME;
        byte[] image;
        byte[] draw;
        String  TEXT;
        String VOICE_PATH;
        ArrayList<note_modle> arrayList = new ArrayList<>();
        String query = "SELECT * FROM " + DB.TABLE_NOTE + " ORDER BY id DESC";
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                idd = cursor.getInt(cursor.getColumnIndex(DB.ID));
                TIME = cursor.getString(cursor.getColumnIndex(DB.TIME));
                TEXT = cursor.getString(cursor.getColumnIndex(DB.TEXT));
                VOICE_PATH = cursor.getString(cursor.getColumnIndex(DB.VOICE_PATH));
                SUBTITLE = cursor.getString(cursor.getColumnIndex(DB.SUBTITLE));
                draw = cursor.getBlob(cursor.getColumnIndex(DB.DRAW));
                image = cursor.getBlob(cursor.getColumnIndex(DB.IMG));
                TITLE = cursor.getString(cursor.getColumnIndex(DB.TITLE));
                arrayList.add(new note_modle(VOICE_PATH,TEXT,TIME,TITLE,SUBTITLE,image,idd,draw));

            }while (cursor.moveToNext());
        }
        return arrayList;
    }
    public boolean deleteBYid(String id) {
        try {
            database.delete(DB.TABLE_NOTE, "id = " + id, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

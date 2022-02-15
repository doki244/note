package com.example.note_paad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;



import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    NoteDataAccess access = new NoteDataAccess(this);
    private RecyclerView recyclerView;
    private TextView inputSearch;
    private ImageView theme;
    private LinearLayout layoutSearch;
    private ConstraintLayout constraint;

    static theme light;
    static theme dark;
    public static final int light_theme=1;
    public static  int curent_theme=-1;
    public static final int dark_theme=2;
    // private AppBarConfiguration appBarConfiguration;
    //private ActivityMainBinding binding;
    FloatingActionButton add_btn;
    public static ArrayList<note_modle> notes;
    adapter ada ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        add_btn= findViewById(R.id.fab);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        theme = findViewById(R.id.theme);
        constraint = findViewById(R.id.constraint);

        layoutSearch = findViewById(R.id.layoutSearch);

        if (sharedPref.getInt("theme",-1)==-1){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("theme", light_theme);
            editor.apply();
        }

        light = new theme(Color.parseColor("#b5b49e"),Color.parseColor("#9a8971"),Color.parseColor("#2F2D2E"),Color.parseColor("#2F2D2E"),Color.parseColor("#e85d18"),Color.parseColor("#EEE3CC"));
        dark = new theme(Color.parseColor("#292929"),Color.parseColor("#333333"),Color.parseColor("#FFFFFF"),Color.parseColor("#FFFFFF"),Color.parseColor("#5C5CFF"),Color.parseColor("#292929"));
        inputSearch= findViewById(R.id.inputSearch);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CreatNoteActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView = findViewById(R.id.notesRecyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));///////
        notes= new ArrayList<>();
        //setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        access.openDB();
        //access.addNewNote(new note_modle("2","text",new Date().getTime()+"","title","sub",null));
        notes = access.getall();
         ada = new adapter(notes);
         recyclerView.setAdapter(ada);
        access.closeDB();
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<note_modle> note_searched = new ArrayList<>();
                for (note_modle note : notes) {
                    if(note.getTitle().matches("(?i)("+charSequence+").*")){
                        Log.i("notess", note.toString());
                        note_searched.add(note);

                    }
                }
                ada.n_();
                ada = new adapter(note_searched);
                recyclerView.setAdapter(ada);
                if (charSequence.length()==0){
                    ada.n_();
                    ada = new adapter(notes);
                    recyclerView.setAdapter(ada);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (sharedPref.getInt("theme",-1)==dark_theme){
            //linearLayout.setBackgroundColor(dark.getItem());
            constraint.setBackgroundColor(dark.getBackground());
            inputSearch.setHintTextColor(Color.parseColor("#7b7b7b"));
            add_btn.setRippleColor(dark.getFloatingActionButton());
            //ada.notifyDataSetChanged();
            layoutSearch.setBackgroundResource(R.drawable.search_background_dark);
            theme.setImageResource(R.drawable.moon);
            curent_theme=dark_theme;
        }else if (sharedPref.getInt("theme",-1)==light_theme){
            constraint.setBackgroundColor(light.getBackground());
            add_btn.setRippleColor(light.getFloatingActionButton());
            inputSearch.setHintTextColor(light.getText_color());
            //ada.notifyDataSetChanged();
            layoutSearch.setBackgroundResource(R.drawable.search_background);
            theme.setImageResource(R.drawable.sun);
            curent_theme=light_theme;
        }
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPref.getInt("theme",-1)==1){//go to dark
                    //linearLayout.setBackgroundColor(dark.getItem());
                    constraint.setBackgroundColor(dark.getBackground());
                    inputSearch.setHintTextColor(Color.parseColor("#7b7b7b"));
                    add_btn.setRippleColor(dark.getFloatingActionButton());
                    ada.notifyDataSetChanged();
                    layoutSearch.setBackgroundResource(R.drawable.search_background_dark);
                    theme.setImageResource(R.drawable.moon);

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.putInt("theme", dark_theme);
                    editor.apply();
                    curent_theme=dark_theme;
                }else if (sharedPref.getInt("theme",-1)==2){//go to light

                    constraint.setBackgroundColor(light.getBackground());
                    add_btn.setRippleColor(light.getFloatingActionButton());
                    inputSearch.setHintTextColor(light.getText_color());
                    ada.notifyDataSetChanged();
                    layoutSearch.setBackgroundResource(R.drawable.search_background);
                    theme.setImageResource(R.drawable.sun);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.putInt("theme", light_theme);
                    editor.apply();
                    curent_theme=light_theme;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Log.i("TAG", "onOptionsItemSelected: ");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}